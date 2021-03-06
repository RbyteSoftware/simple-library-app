package ru.simplelib.library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.simplelib.library.config.auth.SimpleBase64AuthToken;
import ru.simplelib.library.controller.transfer.auth.AuthenticationResponseDto;
import ru.simplelib.library.controller.transfer.auth.BasicTokenWithRole;
import ru.simplelib.library.controller.transfer.auth.CredentialPairDto;
import ru.simplelib.library.domain.User;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/authenticate",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody CredentialPairDto credential) {
        log.info("Authenticating user with username {}", credential.getUsername());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                credential.getUsername(), credential.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(auth);
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return ResponseEntity.ok(new AuthenticationResponseDto(
                        true, Instant.now(), "", new BasicTokenWithRole(
                        SimpleBase64AuthToken.getToken((User) authentication.getPrincipal()),
                        extractAuthority(authentication)
                )));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponseDto(
                    false, Instant.now(), e.getMessage(), null
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new AuthenticationResponseDto(
                        false, Instant.now(), "Bad Credentials", null
                ));
    }

    private Set<String> extractAuthority(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
