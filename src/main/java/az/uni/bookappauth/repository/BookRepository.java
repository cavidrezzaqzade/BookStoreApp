package az.uni.bookappauth.repository;

import az.uni.bookappauth.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    boolean existsByBookNameIgnoreCase(String roleName);
    List<BookEntity> findByPublisherId(Long id);
}
