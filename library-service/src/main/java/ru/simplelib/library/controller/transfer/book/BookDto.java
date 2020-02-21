package ru.simplelib.library.controller.transfer.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookDto {
    private Long id;
    private String isbNumber;
    private String title;
    private String author;
    /**
     * Calculated field, analyze BookCardEvents
     */
    private BookRelease bookRelease;
}
