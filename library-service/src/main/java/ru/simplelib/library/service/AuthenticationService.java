package ru.simplelib.library.service;

import ru.simplelib.library.domain.User;

public interface AuthenticationService {
    User getUserFromSession();
}
