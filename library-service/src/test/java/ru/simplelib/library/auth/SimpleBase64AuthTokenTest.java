package ru.simplelib.library.auth;

import org.junit.jupiter.api.Test;
import ru.simplelib.library.config.auth.SimpleBase64AuthToken;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleBase64AuthTokenTest {
    @Test
    public void shouldEncodeData() {
        assertEquals(SimpleBase64AuthToken.getToken(
                "Admin", "Admin"
        ), "QWRtaW46QWRtaW4=");
    }

    @Test
    public void shouldTest() {
        System.out.println(SimpleBase64AuthToken.getToken("Admin", "$2a$10$8/jsAvuu/5LNftzdUFhLte8FUD8OOT9Vh4UWbAsCEWGQwCz839b96"));
    }

    @Test
    public void shouldDecodeData() {
        SimpleBase64AuthToken token = SimpleBase64AuthToken.byToken("QWRtaW46QWRtaW4=");
        assertEquals(token.getUsername(), "Admin");
        assertEquals(token.getPassword(), "Admin");
    }
}