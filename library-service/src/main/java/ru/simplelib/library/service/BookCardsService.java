package ru.simplelib.library.service;

import ru.simplelib.library.domain.BookEvent;
import ru.simplelib.library.exceptions.ServiceModificationException;


/**
 * @author Mikhail Yuzbashev
 */
public interface BookCardsService {

    void addCardEvent(Long bookId, BookEvent event) throws ServiceModificationException;

}
