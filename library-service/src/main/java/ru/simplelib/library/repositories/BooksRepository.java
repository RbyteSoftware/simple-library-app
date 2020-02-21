package ru.simplelib.library.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.simplelib.library.domain.Book;

public interface BooksRepository extends CrudRepository<Book, Long> {
    @Query("select count(id) from Book")
    Long getCount();
}
