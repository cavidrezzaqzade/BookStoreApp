package az.uni.bookappauth.service;

import az.uni.bookappauth.domain.Role;
import az.uni.bookappauth.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private static JwtProvider jwtProvider;

    private static User user;

    @BeforeAll
    static void setUpAll(){

        jwtProvider = new JwtProvider("b00k@pp", "b00k@pp_r3fr3sh");

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().roleName("ADMIN").build());
        roles.add(Role.builder().roleName("USER").build());

        user = User.builder()
                .login("cavid")
                .password("12345")
                .roles(roles)
                .build();

    }
    @DisplayName("check generateAccessToken")
    @Test
    void givenUser_WhenGenerateAccessToken_ThenOk() {
        //given

        //when
        String accessToken = jwtProvider.generateAccessToken(user);

        //then
        assertFalse(accessToken.isEmpty());


    }

    @Test
    void generateRefreshToken() {
    }

    @Test
    void validateAccessToken() {
    }

    @Test
    void validateRefreshToken() {
    }

    @Test
    void getAccessClaims() {
    }

    @Test
    void getRefreshClaims() {
    }
}