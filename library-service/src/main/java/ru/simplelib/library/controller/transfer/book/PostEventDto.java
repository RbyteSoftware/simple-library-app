package ru.simplelib.library.controller.transfer.book;

import lombok.Data;
import ru.simplelib.library.domain.BookEvent;

@Data
public class PostEventDto {
    private BookEvent bookEvent;
    private Long bookId;
}
