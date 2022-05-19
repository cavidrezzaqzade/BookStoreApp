package az.uni.bookappauth.controller;

import az.uni.bookappauth.domain.app.BookDto;
import az.uni.bookappauth.domain.app.SearchBookDto;
import az.uni.bookappauth.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("app")
@RequiredArgsConstructor
@Tag(name = "Book", description = "the book API")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "get books", description = "get all books", tags = {"Book"}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("books")
    public ResponseEntity<?> getBooks() {
        return bookService.getBooks();
    }

    @Operation(summary = "search books", description = "search books", tags = {"Book"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("searchbooks")
    public ResponseEntity<?> searchBooks(@Parameter(description = "search for books in the database", required = true) @Valid @RequestBody SearchBookDto dto,
                                         @RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return bookService.searchBooks(dto, pageNo, pageSize, sortBy);
    }

    @Operation(summary = "get book by id", description = "get book by id", tags = {"Book"}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable(value = "id") Long id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "get book by publisher", description = "get book by publisher", tags = {"Book"}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("books/users/{id}")
    public ResponseEntity<?> getBookByPublisher(@PathVariable(value = "id") Long id) {
        return bookService.getBookByPublisher(id);
    }

    @Operation(summary = "add book", description = "add new book", tags = {"Book"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('PUBLISHER')")
    @PostMapping("books")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDto book, Authentication auth) {
        return  bookService.addBook(book, auth);
    }

    @Operation(summary = "update my book", description = "update the existing book", tags = {"Book"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('PUBLISHER')")
    @PutMapping("books/{id}")
    public ResponseEntity<?> updateBook(@Parameter(description = "Update an existing book in the database", required = true)
                                        @Valid @RequestBody BookDto book,
                                        @PathVariable(value = "id") Long id,
                                        Authentication auth) {
        return bookService.updateBook(book, id, auth);
    }

}
