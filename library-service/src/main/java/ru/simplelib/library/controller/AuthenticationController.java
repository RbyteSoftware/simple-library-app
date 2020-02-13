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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.simplelib.library.controller.transfer.auth.AuthenticationResponseDto;
import ru.simplelib.library.controller.transfer.auth.BasicTokenWithRole;
import ru.simplelib.library.controller.transfer.auth.CredentialPairDto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody CredentialPairDto credential) {
        // FIXME: remove auth logic from Controller
        log.info("Authenticating user with username {}", credential.getUsername());

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                credential.getUsername(), credential.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            if (authentication.isAuthenticated()) {
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    log.info("Have role {}", authority.getAuthority());
                }
                return ResponseEntity.ok(new AuthenticationResponseDto(
                        true, Instant.now(), "", new BasicTokenWithRole(
                        // todo: generate token
                        UUID.randomUUID().toString(),
                        extractAuthority(authentication)
                )));
            }
        } catch (Exception e) {
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
