package ru.simplelib.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.simplelib.library.domain.BookCardEvents;

public interface BookCardEventsRepository extends CrudRepository<BookCardEvents, Long> {
}
