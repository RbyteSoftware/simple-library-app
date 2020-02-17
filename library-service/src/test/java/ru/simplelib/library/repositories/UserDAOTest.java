package ru.simplelib.library.repositories;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.simplelib.library.config.Datasource;
import ru.simplelib.library.domain.Person;
import ru.simplelib.library.domain.Role;
import ru.simplelib.library.domain.User;
import ru.simplelib.library.exceptions.ServiceModificationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureDataJdbc
@SpringBootTest(classes = {Datasource.class, UserDAOImpl.class})
public class UserDAOTest {
    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDAO userDAO;

    @BeforeEach
    public void init() {
        given(passwordEncoder.encode(anyString())).willReturn(
                "$2a$10$8/jsAvuu/5LNftzdUFhLte8FUD8OOT9Vh4UWbAsCEWGQwZz839b96"
        );
    }

    @Test
    public void shouldDuplicateKeyOnCreate() throws ServiceModificationException {
        assertThrows(ServiceModificationException.class, () -> {
            User user = new User();
            user.setLogin("Admin");
            user.setPassword(passwordEncoder.encode("abcd"));
            user.setPerson(new Person("Neo", "Mr.Anderson", "anderson@maitrix.com"));
            user.setRoles(Stream.of(new Role(null, "ADMIN"), new Role(null, "USER")).collect(Collectors.toSet()));
            User created = userDAO.create(user);
        }, "Not Duplicate Key");
    }

    @Test
    public void shouldCreateUser() throws ServiceModificationException {
        User user = new User();
        user.setLogin("Admin2");
        user.setPassword(passwordEncoder.encode("abcd"));
        user.setPerson(new Person("Neo", "Mr.Anderson", "anderson@maitrix.com"));
        user.setRoles(Stream.of(new Role(null, "ADMIN"), new Role(null, "USER")).collect(Collectors.toSet()));
        User created = userDAO.create(user);
        assertNotNull(created.getId());
    }

    @Test
    public void shouldFindByLogin() {
        Optional<User> userOptional = userDAO.findOneByLogin("Admin");
        assertTrue(userOptional.isPresent());
    }

    @Test
    public void shouldFindOnePage() {
        List<User> userList = userDAO.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "login")));
        assertEquals(userList.size(), 2);
    }

}
