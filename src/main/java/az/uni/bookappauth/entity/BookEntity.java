package az.uni.bookappauth.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books", schema = "public")
public class BookEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "book_name", nullable = false, unique = true)
    private String bookName;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "publisher_id", nullable = false)
    private Long publisherId;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @UpdateTimestamp
    private LocalDateTime updated;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AuthorEntity> authors = new ArrayList<>();

    public void addAuthor(AuthorEntity author) {
        authors.add(author);
        author.setBook(this);
    }

    public void removeAuthor(AuthorEntity author) {
        authors.remove(author);
        author.setBook(null);
    }
}
