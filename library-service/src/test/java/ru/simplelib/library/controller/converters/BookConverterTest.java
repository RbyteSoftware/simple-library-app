package ru.simplelib.library.controller.converters;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.simplelib.library.controller.transfer.book.BookDto;
import ru.simplelib.library.domain.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class BookConverterTest {

    private BookConverter bookConverter;

    @BeforeEach
    public void setUp() {
        bookConverter = new BookConverter();
    }

    @Test
    public void shouldCovertWithoutEvents() {
        Book book = createBook();
        BookDto bookDto = bookConverter.from(book);
        assertEquals(book.getId(), 1L);
        assertEquals(book.getIsbNumber(), "222-222-2222-22");
        assertEquals(book.getAuthor(), "JUnit5");
        assertEquals(book.getTitle(), "Test Book");
        assertNull(book.getBookCardEvents());
    }

    @Test
    public void shouldGetLastEvent() {
        Book book = createBook();
        book.setBookCardEvents(createMixedOrderDates(9));
        // todo: end this test
        BookDto bookDto = bookConverter.from(book);
    }

    private Book createBook() {
        return new Book(1L, "222-222-2222-22", "JUnit5", "Test Book", null);
    }

    private Set<BookCardEvent> createMixedOrderDates(int eventCount) {
        Set<BookCardEvent> events = new HashSet<>(eventCount);
        for (int i = 0; i < eventCount; i++) {
            BookEvent bookEvent = BookEvent.values()[i % 2];
            BookCardEvent cardEvent = new BookCardEvent((long) i, new Book().getId(), bookEvent, getSurrogateUser().getId(), getTimeWithRandomHourFunction(i));
            events.add(cardEvent);
        }
        return events;
    }

    private LocalDateTime getTimeWithRandomHourFunction(int iteration) {
        LocalDateTime now = LocalDateTime.now();
        int randomHour = ThreadLocalRandom.current().nextInt(1, 5);
        return (iteration % 2 == 0) ? now.plusHours(randomHour) : now.minusHours(randomHour);
    }

    private User getSurrogateUser() {
        return new User("test", "test", new Person("Lisa", "Carry", "lisa@io.ru"));
    }
}