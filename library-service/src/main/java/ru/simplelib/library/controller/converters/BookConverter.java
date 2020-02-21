package ru.simplelib.library.controller.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.simplelib.library.controller.transfer.book.BookDto;
import ru.simplelib.library.controller.transfer.book.BookRelease;
import ru.simplelib.library.domain.Book;
import ru.simplelib.library.domain.BookCardEvent;
import ru.simplelib.library.domain.BookEvent;
import ru.simplelib.library.domain.User;
import ru.simplelib.library.repositories.UserDAO;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookConverter implements Converter<Book, BookDto> {

    @Autowired
    UserDAO userDAO;

    @Override
    public BookDto from(Book book) {
        Long userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> currentUser = userDAO.findOneByLogin((String) authentication.getPrincipal());
        if (currentUser.isPresent()) {
            userId = currentUser.get().getId();
        }
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbNumber(book.getIsbNumber());
        bookDto.setTitle(book.getTitle());
        BookRelease bookRelease = calculateReleaseEvent(book, userId).orElse(new BookRelease());
        bookDto.setBookRelease(bookRelease);
        return bookDto;
    }

    @Override
    public Book to(BookDto bookDto) {


        return null;
    }

    private Optional<BookRelease> calculateReleaseEvent(Book book, Long userId) {
        // todo: need to refactor to service
        Set<BookCardEvent> events = book.getBookCardEvents();
        if (Objects.nonNull(events) && !events.isEmpty()) {
            ArrayList<BookCardEvent> filtredEvents = events.stream().filter(
                    it -> BookEvent.RELEASE_BOOK.equals(it.getEvent()) || BookEvent.TAKE_BOOK.equals(it.getEvent())
            ).sorted(Comparator.comparing(BookCardEvent::getId))
                    .collect(Collectors.toCollection(ArrayList::new));
            BookCardEvent lastEvent = filtredEvents.get(filtredEvents.size() - 1);
            return Optional.of(new BookRelease(
                    BookEvent.RELEASE_BOOK.equals(lastEvent.getEvent()),
                    Objects.equals(lastEvent.getCreatedBy(), userId)
            ));
        }
        return Optional.empty();
    }
}
