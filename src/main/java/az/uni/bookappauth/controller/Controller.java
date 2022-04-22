package az.uni.bookappauth.controller;

import az.uni.bookappauth.domain.JwtAuthentication;
import az.uni.bookappauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Test", description = "test controller")
public class Controller {

    private final AuthService authService;

    @Operation(summary = "test", description = "test", tags = {"Test"})
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("hello/user")
    public ResponseEntity<String> helloUser() {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }

    @Operation(summary = "test", description = "test", tags = {"Test"})
    @PreAuthorize("hasAuthority('PUBLISHER')")
    @GetMapping("hello/publisher")
    public ResponseEntity<String> helloPublisher() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello publisher " + authInfo.getPrincipal() + "!");
    }

    @Operation(summary = "test", description = "test", tags = {"Test"})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("hello/admin")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
    }


}
