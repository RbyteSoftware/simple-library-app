package ru.simplelib.library.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.simplelib.library.domain.User;
import ru.simplelib.library.repositories.UserDAO;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDAO userDAO;

    public AuthenticationServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserFromSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> currentUser = userDAO.findOneByLogin((String) authentication.getPrincipal());
        return currentUser.orElse(null);
    }
}
