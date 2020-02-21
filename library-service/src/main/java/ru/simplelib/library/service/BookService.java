package ru.simplelib.library.service;

import ru.simplelib.library.domain.Book;

import java.util.List;

/**
 * @author Mikhail Yuzbashev
 */
public interface BookService {

    Long getBookCount();

    List<Book> getList(Integer pageNum, Integer perPage, String sortFieldName, String sortDirection);
}
