package az.uni.bookappauth.repository;

import az.uni.bookappauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsernameIgnoreCase(String name);
    Optional<UserEntity> findByUsernameIgnoreCase(String userName);
}
