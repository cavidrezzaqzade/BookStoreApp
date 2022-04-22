package az.uni.bookappauth.repository;

import az.uni.bookappauth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    boolean existsByRoleNameIgnoreCase(String roleName);

    Optional<RoleEntity> findByRoleNameIgnoreCase(String roleName);

    @Query(value = "select exists(select * from users_roles ur where ur.role_id = :id)", nativeQuery = true)
    boolean checkForeignKeyExists(Long id);

    @Query(value = "select r.id from roles r", nativeQuery = true)
    List<Long> findAllIds();

}
