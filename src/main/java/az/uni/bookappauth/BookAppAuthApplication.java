package az.uni.bookappauth;

import az.uni.bookappauth.domain.User;
import az.uni.bookappauth.entity.RoleEntity;
import az.uni.bookappauth.entity.UserEntity;
import az.uni.bookappauth.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Set;

@OpenAPIDefinition(info = @Info(title = "AERS API", version = "1.0", description = "authentication service for aers"))
@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class BookAppAuthApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BookAppAuthApplication.class, args);

        //startup time initial data inserting to database as admin user
        UserRepository user = configurableApplicationContext.getBean(UserRepository.class);
        UserEntity userEntity = UserEntity.builder()
                .username("cavid")
                .password(new BCryptPasswordEncoder().encode("12345"))
                .name("cavid")
                .surname("rezzaqzade")
                .status(true)
                .roles(Set.of(RoleEntity.builder().roleName("ADMIN").status(true).build()))
                .build();

        user.save(userEntity);
    }

}
