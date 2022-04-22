package az.uni.bookappauth.service;

import az.uni.bookappauth.domain.JwtAuthentication;
import az.uni.bookappauth.domain.JwtRequest;
import az.uni.bookappauth.domain.JwtResponse;
import az.uni.bookappauth.domain.User;
import az.uni.bookappauth.exception.AuthException;
import az.uni.bookappauth.response.MessageResponse;
import az.uni.bookappauth.response.Reason;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public ResponseEntity<?> login(@NonNull JwtRequest authRequest) {
        log.info("authService/login method started");
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("User is not found"));
//        if (user.getPassword().equals(authRequest.getPassword())) {
        if (new BCryptPasswordEncoder().matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            log.info("authService/login method ended -> status:" + HttpStatus.OK);
            return MessageResponse.response(Reason.SUCCESS_GET.getValue(), new JwtResponse(accessToken, refreshToken), null, HttpStatus.OK);
//            return new JwtResponse(accessToken, refreshToken);
        } else {
            log.error("authService/login method ended with wrong password -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
            throw new AuthException("wrong password");
        }
    }

    public ResponseEntity<?> getAccessToken(@NonNull String refreshToken) {
        log.info("authService/getAccessToken method started");
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User is not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
//                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
//                refreshStorage.remove(user.getLogin());
//                refreshStorage.put(user.getLogin(), newRefreshToken);
                log.info("authService/getAccessToken method ended -> status:" + HttpStatus.OK);
                return MessageResponse.response(Reason.SUCCESS_GET.getValue(), new JwtResponse(accessToken, null), null, HttpStatus.OK);
//                return new JwtResponse(accessToken, null);
            }
        }
        log.error("authService/getAccessToken method ended with invalid jwt token -> status:" + HttpStatus.UNPROCESSABLE_ENTITY);
        throw new AuthException("Invalid JWT token");
//        return new JwtResponse(null, null);
    }

    public ResponseEntity<?> refresh(@NonNull String refreshToken) {
        log.info("authService/getAccessToken method started");
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User is not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                log.info("authService/getAccessToken method ended");
                return MessageResponse.response(Reason.SUCCESS_GET.getValue(), new JwtResponse(accessToken, newRefreshToken), null, HttpStatus.OK);
//                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        log.info("authService/getAccessToken method ended with invalid jwt token");
        throw new AuthException("Invalid JWT token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
