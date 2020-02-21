package ru.simplelib.library.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.simplelib.library.config.Datasource;
import ru.simplelib.library.domain.BookCardEvent;
import ru.simplelib.library.domain.BookEvent;
import ru.simplelib.library.domain.Person;
import ru.simplelib.library.domain.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Datasource.class})
@AutoConfigureJdbc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Slf4j
class BookCardEventsRepositoryTest {
    @Autowired
    BookCardEventsRepository bookCardEventsRepository;

    @Test
    public void contextLoad() {
    }

    @Test
    public void testFindByBookId() {
        Optional<List<BookCardEvent>> eventOpt = bookCardEventsRepository.findByBookId(1L);
        assertTrue(eventOpt.isPresent());
        assertFalse(eventOpt.get().isEmpty());
        eventOpt.ifPresent(bookCardEvents -> log.info("events = {}", bookCardEvents));
    }

    @Test
    public void shouldSaveEvent() {
        User user = new User("aaaa", "bbbb", new Person());
        user.setId(1L);

        BookCardEvent saved = bookCardEventsRepository.save(
                new BookCardEvent(1L, BookEvent.RELEASE_BOOK, user.getId()));

        assertNotNull(saved.getId());

    }

}