package ru.simplelib.library.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void shouldCreateEmpty() {
        User user = new User();
        assertNull(user.getLogin());
        assertFalse(user.hasRoles());
    }

    @Test
    public void shouldCreateFull() {
        User user = createAndFillUser();
        assertNotNull(user.getLogin());
        assertNotNull(user.getPassword());
        assertNotNull(user.getPerson());
        assertFalse(user.hasRoles());
    }

    @Test
    public void shouldEquals() {
        User user1 = createAndFillUser(), user2 = createAndFillUser();
        assertEquals(user1, user2);
    }

    @Test
    public void shouldAcceptRole() {
        User user1 = createAndFillUser(), user2 = createAndFillUser();
        final Role role = new Role(1L, "ROLE_USER");
        user1.addRole(role);
        assertTrue(user1.hasRoles());
        assertTrue(user1.getRoles().contains(role));

        final ArrayList<Role> roles = Stream.of(new Role(1L, "ROLE_USER"), new Role(2L, "ROLE_ADMIN"))
                .collect(Collectors.toCollection(ArrayList::new));
        user2.addRoles(roles);
        assertTrue(user2.hasRoles());
        assertTrue(user2.getRoles().containsAll(roles));
    }

    private User createAndFillUser() {
        return new User(
                "testLogin",
                "testPwd",
                new Person()
        );
    }

}