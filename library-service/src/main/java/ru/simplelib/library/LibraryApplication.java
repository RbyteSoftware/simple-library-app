package ru.simplelib.library;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.simplelib.library.repositories.UserDAO;

@SpringBootApplication
@Slf4j
public class LibraryApplication {

    private final UserDAO userDAO;

    @Autowired
    public LibraryApplication(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}
