package az.uni.bookappauth.service.app;

import az.uni.bookappauth.domain.app.BookDto;
import az.uni.bookappauth.domain.app.SearchBookDto;
import az.uni.bookappauth.entity.AuthorEntity;
import az.uni.bookappauth.entity.BookEntity;
import az.uni.bookappauth.entity.RoleEntity;
import az.uni.bookappauth.entity.UserEntity;
import az.uni.bookappauth.mapper.AuthorMapper;
import az.uni.bookappauth.mapper.BookMapper;
import az.uni.bookappauth.repository.AuthorRepository;
import az.uni.bookappauth.repository.BookRepository;
import az.uni.bookappauth.repository.UserRepository;
import az.uni.bookappauth.response.MessageResponse;
import az.uni.bookappauth.response.Reason;
import az.uni.bookappauth.search.BookSpecification;
import az.uni.bookappauth.search.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    public ResponseEntity<?> searchBooks(SearchBookDto dto, Integer pageNo, Integer pageSize, String sortBy){
        log.info("BookService/searchBooks method started");

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        BookSpecification spec1 =
                new BookSpecification(new SearchCriteria("bookName", ":", dto.getBookName()));
        BookSpecification spec2 =
                new BookSpecification(new SearchCriteria("pageCount", "=", dto.getPageCount()));

        Page<BookEntity> books =
                bookRepository.findAll(Specification.where(spec1).and(spec2), paging);

        List<BookDto> bookDtos;

        if(books.hasContent())
            bookDtos = bookMapper.booksToBooksDto(books.getContent());
        else
            bookDtos = new ArrayList<>();

        log.info("BookService/searchBooks method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_GET.getValue(), bookDtos, null, HttpStatus.OK);
    }
    public ResponseEntity<?> getBooks(){
        log.info("BookService/getBooks method started");
        List<BookEntity> books = bookRepository.findAll();
        List<BookDto> bookDtos = bookMapper.booksToBooksDto(books);
        log.info("BookService/getBooks method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_GET.getValue(), bookDtos, null, HttpStatus.OK);
    }

    public ResponseEntity<?> addBook(BookDto book, Authentication auth){
        log.info("BookService/addBook method started");
        Map<String, String> map = new HashMap<>();

        if(bookRepository.existsByBookNameIgnoreCase(book.getBookName()))
            map.put("bookName", "data already exists");
        if(!map.isEmpty()) {
            log.error("BookService/addBook method ended with bookName already exists -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
        }

//        BookEntity bookEntity = BookEntity.builder()
//                .bookName(book.getBookName())
//                .pageCount(book.getPageCount())
//                .publisherId(book.getPublisherId())
//                .authors(authorMapper.authorsDtoToAuthors(book.getAuthors()))
//                .build();

        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName(book.getBookName());
        bookEntity.setPageCount(book.getPageCount());
        bookEntity.setPublisherId(userRepository.findByUsernameIgnoreCase(auth.getPrincipal().toString()).get().getId());

        book.getAuthors().forEach(e -> bookEntity.addAuthor(authorMapper.authorDtoToAuthor(e)));

        bookRepository.save(bookEntity);
        BookDto bookDto = bookMapper.bookToBookDto(bookEntity);
        log.info("BookService/addBook method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_ADD.getValue(), bookDto, null, HttpStatus.OK);
    }

    public ResponseEntity<?> getBookById(Long id){
        log.info("BookService/getBookById method started");

        Map<String, String> map = new HashMap<>();

        if(!bookRepository.existsById(id))
            map.put("bookId", "data not found");
        if(!map.isEmpty()) {
            log.error("BookService/getBookById method ended with data not found -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        BookEntity book = bookRepository.findById(id).get();
        BookDto bookDto = bookMapper.bookToBookDto(book);
        log.info("BookService/getBookById method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_GET.getValue(), bookDto, null, HttpStatus.OK);
    }

    public ResponseEntity<?> getBookByPublisher(Long userId){
        log.info("BookService/getBookByPublisher method started");

        Map<String, String> map = new HashMap<>();

        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty())
            map.put("userId", "data not found");
        if(user.isPresent()){
            boolean flag = false;
            for(RoleEntity role : user.get().getRoles())
                if(role.getRoleName().toUpperCase(Locale.ROOT).equals("PUBLISHER")) flag = true;
            if(!flag)
                map.put("userId","specified user is not publisher");
        }
        if(!map.isEmpty()) {
            log.error("BookService/getBookByPublisher method ended with data not found -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        List<BookEntity> books = bookRepository.findByPublisherId(userId);
        List<BookDto> booksDto = bookMapper.booksToBooksDto(books);
        log.info("BookService/getBookByPublisher method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_GET.getValue(), booksDto, null, HttpStatus.OK);
    }

    public ResponseEntity<?> updateBook(BookDto book, Long bookId, Authentication auth){
        log.info("BookService/updateBook method started");
        Map<String, String> map = new HashMap<>();

        Optional<BookEntity> bookEntity = bookRepository.findById(bookId);

        if(bookEntity.isEmpty())
            map.put("bookId", "data does not exist");
        Long userId = userRepository.findByUsernameIgnoreCase(auth.getPrincipal().toString()).get().getId();
        if(bookEntity.get().getPublisherId() != userId)
            map.put("bookId", "publisher is not owner this book");
        if(!map.isEmpty()) {
            log.error("BookService/updateBook method ended with bookId doesn't exist -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
            return MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        bookEntity.get().setBookName(book.getBookName());
        bookEntity.get().setPageCount(book.getPageCount());
        bookEntity.get().setPublisherId(userId);

        List<AuthorEntity> authors = bookEntity.get().getAuthors();
        bookEntity.get().getAuthors().removeAll(authors);
        book.getAuthors().forEach(e -> bookEntity.get().addAuthor(authorMapper.authorDtoToAuthor(e)));

        bookRepository.save(bookEntity.get());
        BookDto bookDto = bookMapper.bookToBookDto(bookEntity.get());

        log.info("BookService/updateBook method ended -> status:" + HttpStatus.OK);
        return MessageResponse.response(Reason.SUCCESS_UPDATE.getValue(), bookDto, null, HttpStatus.OK);
    }
}
