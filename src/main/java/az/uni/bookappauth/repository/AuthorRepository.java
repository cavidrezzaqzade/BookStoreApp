package az.uni.bookappauth.repository;

import az.uni.bookappauth.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
