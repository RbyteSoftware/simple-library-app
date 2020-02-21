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
import ru.simplelib.library.config.JdbcAuditorAwareConfiguration;
import ru.simplelib.library.domain.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Datasource.class, JdbcAuditorAwareConfiguration.class})
@AutoConfigureJdbc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Slf4j
class BooksRepositoryTest {
    @Autowired
    BooksRepository booksRepository;

    @Autowired
    BookCardEventsRepository bookCardEventsRepository;

    @Test
    public void contextLoad() {
    }

    @Test
    public void shouldGetBookById() {
        Optional<Book> book = booksRepository.findById(1L);
        assertTrue(book.isPresent());

        log.info("book {}", book);
    }


    @Test
    public void shouldCreateBookRecord() {
        Book book = new Book("2-266-11156-6", "Robert Martin", "Clean code");
//        Set<BookCardEvent> cardEventsSet = Stream.of(createEvent(BookEvent.TAKE_BOOK), createEvent(BookEvent.RELEASE_BOOK))
//                .collect(Collectors.toCollection(LinkedHashSet::new));

        book.setBookCardEvents(
                null
        );
        Book savedBook = booksRepository.save(book);
        log.info("Saved book = {}", savedBook);

    }

    @Test
    public void getCount() {
        log.info("books count {}", booksRepository.getCount());
    }

    @Test
    public void shouldCardEventAudit() {
        User user = new User("111", "111", new Person());
        user.setId(1L);
        Book book = new Book(1L, "222-222-2222-22", "JUnit5", "Test Book", null);
        Set<BookCardEvent> cardEventsSet = Stream.of(createEvent(BookEvent.TAKE_BOOK, book, user), createEvent(BookEvent.RELEASE_BOOK, book, user))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<BookCardEvent> bookCardEvents = (List<BookCardEvent>) bookCardEventsRepository.saveAll(cardEventsSet);
        log.info("book cards = {}", bookCardEvents);

    }

    private BookCardEvent createEvent(BookEvent event, Book book, User user) {
        return new BookCardEvent(book.getId(), event, user.getId());
    }

}