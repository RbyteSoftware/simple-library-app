package ru.simplelib.library.controller.transfer.auth;

public interface AuthData {
    String getToken();

    Iterable<String> getRoles();
}
