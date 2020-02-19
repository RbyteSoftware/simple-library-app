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
import ru.simplelib.library.domain.Book;
import ru.simplelib.library.domain.BookCardEvents;
import ru.simplelib.library.domain.BookEvent;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void shouldCreateBookRecord() {
        Book book = new Book("2-266-11156-6", "Robert Martin", "Clean code");
        Set<BookCardEvents> cardEventsSet = Stream.of(createEvent(BookEvent.TAKE_BOOK), createEvent(BookEvent.RELEASE_BOOK))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        book.setBookCardEvents(
                cardEventsSet
        );
        Book savedBook = booksRepository.save(book);
        log.info("Saved book = {}", savedBook);

    }

    @Test
    public void shouldCardEventAuditAware() {

        Set<BookCardEvents> cardEventsSet = Stream.of(createEvent(BookEvent.TAKE_BOOK), createEvent(BookEvent.RELEASE_BOOK))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<BookCardEvents> bookCardEvents = (List<BookCardEvents>) bookCardEventsRepository.saveAll(cardEventsSet);

        log.info("audit = {}", bookCardEvents);

    }

    private BookCardEvents createEvent(BookEvent event) {
        return new BookCardEvents(event);
    }

}