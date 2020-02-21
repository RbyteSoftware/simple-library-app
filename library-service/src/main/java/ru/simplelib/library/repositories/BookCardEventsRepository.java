package ru.simplelib.library.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.simplelib.library.domain.BookCardEvent;

import java.util.List;
import java.util.Optional;

public interface BookCardEventsRepository extends CrudRepository<BookCardEvent, Long> {
    @Query("select * from BookCardEvents where bookId = :id")
    Optional<List<BookCardEvent>> findByBookId(@Param("id") Long id);

}
