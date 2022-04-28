package az.uni.bookappauth.repository;

import az.uni.bookappauth.entity.RoleEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    private static final RoleEntity ROLE_ENTITY = RoleEntity.builder().roleName("Admin").build();

    @BeforeAll
    void setUpAll(){
        RoleEntity role = RoleEntity.builder()
                .roleName("Admin")
                .build();

        repository.save(role);
    }

    @DisplayName("check exists by upper RoleName")
    @Test
    void givenUpperRoleNameWhenExistsThenTrue() {
        boolean existsByRoleName = repository.existsByRoleNameIgnoreCase("AdmiN");
        assertThat(existsByRoleName).isTrue();
    }

    @DisplayName("check exists by lower RoleName")
    @Test
    void givenLowerRoleNameWhenExistsThenTrue() {
        boolean existsByRoleName = repository.existsByRoleNameIgnoreCase("admin");
        assertThat(existsByRoleName).isTrue();
    }

    @DisplayName("check does not exist by RoleName")
    @Test
    void givenRoleNameWhenDoesNotExistThenTrue() {
        boolean existsByRoleName = repository.existsByRoleNameIgnoreCase("admi");
        assertThat(existsByRoleName).isFalse();
    }

    /////////////////////////////////////////////////////////////////////////////

    @DisplayName("check finds by upper RoleName")
    @Test
    void givenUpperRoleNameWhenFindsThenTrue() {
        Optional<RoleEntity> role = repository.findByRoleNameIgnoreCase("ADmin");
        if (role.isPresent())
            assertEquals(role.get().getRoleName(), ROLE_ENTITY.getRoleName());
        else
            fail();
    }

    @DisplayName("check finds by lower RoleName")
    @Test
    void givenLowerRoleNameWhenFindsThenTrue() {
        Optional<RoleEntity> role = repository.findByRoleNameIgnoreCase("admin");
        if (role.isPresent())
            assertEquals(role.get().getRoleName(), ROLE_ENTITY.getRoleName());
        else
            fail();
    }

    @DisplayName("check does not find by RoleName")
    @Test
    void givenRoleNameWhenDoesNotFindThenTrue() {
        Optional<RoleEntity> role = repository.findByRoleNameIgnoreCase("amdin");
        assertThat(role.isEmpty()).isTrue();
    }

    /////////////////////////////////////////////////////////////////////////////

    @DisplayName("check finds all id's")
    @Test
    void givenNoneWhenFindsAllIdsThenTrue() {
        RoleEntity role = RoleEntity.builder()
                .roleName("User")
                .build();
        repository.save(role);

        List<Long> ids = repository.findAllIds();

        assertEquals(ids, Arrays.asList(1L, 2L));
    }

    @DisplayName("check does not find all id's")
    @Test
    void givenNoneWhenDoesNotFindAllIdsThenFalse() {
        RoleEntity role = RoleEntity.builder()
                .roleName("User")
                .build();
        repository.save(role);

        List<Long> ids = repository.findAllIds();

        assertNotEquals(ids, Arrays.asList(3L, 4L));
    }
}