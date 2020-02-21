package ru.simplelib.library.controller.transfer.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRelease {
    private Boolean isRelease = true;
    private Long userId;
}
