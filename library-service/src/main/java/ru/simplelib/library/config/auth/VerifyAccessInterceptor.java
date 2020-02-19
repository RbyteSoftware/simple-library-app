package ru.simplelib.library.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.simplelib.library.domain.User;
import ru.simplelib.library.repositories.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class VerifyAccessInterceptor implements HandlerInterceptor {
    @Autowired
    UserDAO userDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // FIXME: create token storage
        String authorizationHeader = request.getHeader("Authorization");
        // get header from request
        if (Objects.nonNull(authorizationHeader)) {
            SimpleBase64AuthToken token = SimpleBase64AuthToken.byToken(extractToken(authorizationHeader));
            Optional<User> userOpt = userDAO.findOneByLogin(token.getUsername());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // validate token by equals
                if (SimpleBase64AuthToken.validate(token, SimpleBase64AuthToken.getToken(user))) {
                    // restore session
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword(), user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        return true;
    }

    private String extractToken(String headerValue) {
        // FIXME: need a good method for validate and extract token string
        return Arrays.asList(headerValue.split(" ")).get(1);
    }
}
