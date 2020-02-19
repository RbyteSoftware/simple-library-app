package ru.simplelib.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.simplelib.library.domain.Book;

public interface BooksRepository extends CrudRepository<Book, Long> {
}
