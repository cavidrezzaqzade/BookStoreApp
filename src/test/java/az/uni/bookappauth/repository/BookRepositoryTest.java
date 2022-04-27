package az.uni.bookappauth.repository;

import az.uni.bookappauth.entity.AuthorEntity;
import az.uni.bookappauth.entity.BookEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    void setUpAll(){
        BookEntity book = BookEntity.builder()
                .bookName("Go1984")
                .publisherId(1L)
                .authors(new ArrayList<>(List.of(AuthorEntity.builder().name("George Orwell").build())))
                .build();

        bookRepository.save(book);
    }

    @DisplayName("check exists by upper BookName")
    @Test
    void givenUpperBookNameWhenExistsThenTrue() {
        boolean existsByBookName = bookRepository.existsByBookNameIgnoreCase("GO1984");
        assertThat(existsByBookName).isTrue();
    }

    @DisplayName("check exists by lower BookName")
    @Test
    void givenLowerBookNameWhenExistsThenTrue() {
        boolean existsByBookName = bookRepository.existsByBookNameIgnoreCase("go1984");
        assertThat(existsByBookName).isTrue();
    }

    @DisplayName("check does not exist by upper BookName")
    @Test
    void givenUpperBookNameWhenExistsThenFalse() {
        boolean existsByBookName = bookRepository.existsByBookNameIgnoreCase("1984GO");
        assertThat(existsByBookName).isFalse();
    }

    @DisplayName("check does not exist by lower BookName")
    @Test
    void givenLowerBookNameWhenExistsThenFalse() {
        boolean existsByBookName = bookRepository.existsByBookNameIgnoreCase("1984go");
        assertThat(existsByBookName).isFalse();
    }

    @DisplayName("check finds by PublisherId")
    @Test
    void givenPublisherIdWhenFindsThenTrue() {
        List<BookEntity> book = bookRepository.findByPublisherId(1L);
        assertEquals(1, book.size());
    }

    @DisplayName("check does not find by PublisherId")
    @Test
    void givenPublisherIdWhenDoesNotFindThenFalse() {
        List<BookEntity> book = bookRepository.findByPublisherId(2L);
        assertEquals(0, book.size());
    }
}