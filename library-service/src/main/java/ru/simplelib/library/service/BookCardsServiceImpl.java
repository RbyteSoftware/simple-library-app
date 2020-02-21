package ru.simplelib.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simplelib.library.domain.BookCardEvent;
import ru.simplelib.library.domain.BookEvent;
import ru.simplelib.library.domain.User;
import ru.simplelib.library.exceptions.ServiceModificationException;
import ru.simplelib.library.repositories.BookCardEventsRepository;
import ru.simplelib.library.repositories.UserDAO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class BookCardsServiceImpl implements BookCardsService {
    private final
    BookCardEventsRepository bookCardEventsRepository;

    private final
    UserDAO userDAO;

    public BookCardsServiceImpl(BookCardEventsRepository bookCardEventsRepository, UserDAO userDAO) {
        this.bookCardEventsRepository = bookCardEventsRepository;
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void addCardEvent(Long bookId, BookEvent event) throws ServiceModificationException {
        User user = userDAO.findOneByLogin((String)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .orElse(null);
        Objects.requireNonNull(user, "Cant find user session");
        // implements a simple logic
        Optional<List<BookCardEvent>> eventsOpt = bookCardEventsRepository.findByBookId(bookId);
        if (eventsOpt.isPresent()) {
            List<BookCardEvent> events = eventsOpt.get();
            BookCardEvent lastEvent = events.get(events.size() - 1);
            boolean isOwner = Objects.equals(lastEvent.getCreatedBy(), user.getId());
            boolean isLocked = Objects.equals(BookEvent.TAKE_BOOK, lastEvent.getEvent());
            if (!isOwner && isLocked)
                throw new ServiceModificationException("This book is locked by another User");
            bookCardEventsRepository.save(new BookCardEvent(bookId, event, user.getId()));
        }
    }
}
