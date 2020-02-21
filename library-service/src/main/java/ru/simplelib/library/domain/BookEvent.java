package ru.simplelib.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BookEvent {
    TAKE_BOOK,
    RELEASE_BOOK;
}
