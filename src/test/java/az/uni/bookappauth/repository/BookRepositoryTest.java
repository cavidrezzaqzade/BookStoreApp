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
    void givenUpperBookName_WhenExists_ThenTrue() {
        boolean existsByBookName = bookRepository.existsByBookNameIgnoreCase("GO1984");
        assertThat(existsByBookName).isTrue();
    }

    @DisplayName("check exists by lower BookName")
    @Test
    void givenLowerBookName_WhenExists_ThenTrue() {
        boolean existsByBookName = bookRepository.existsByBookNameIgnoreCase("go1984");
        assertThat(existsByBookName).isTrue();
    }

    @DisplayName("check does not exist by BookName")
    @Test
    void givenBookName_WhenExists_ThenFalse() {
        boolean existsByBookName = bookRepository.existsByBookNameIgnoreCase("1984GO");
        assertThat(existsByBookName).isFalse();
    }

    @DisplayName("check finds by PublisherId")
    @Test
    void givenPublisherId_WhenFinds_ThenTrue() {
        List<BookEntity> book = bookRepository.findByPublisherId(1L);
        assertEquals(1, book.size());
        assertEquals(1L,book.get(0).getId());
    }

    @DisplayName("check does not find by PublisherId")
    @Test
    void givenPublisherId_WhenDoesNotFind_ThenFalse() {
        List<BookEntity> book = bookRepository.findByPublisherId(2L);
        assertEquals(0, book.size());
    }
}