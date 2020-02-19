package ru.simplelib.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Aggregate root object of Book
 *
 * @author Mikhail Yuzbashev
 */
@Data
@NoArgsConstructor
@Table("Book")
public class Book {
    @Id
    private Long id;
    @Column("isbNumber")
    private String isbNumber;
    private String author;
    private String title;
    @MappedCollection(idColumn = "bookId", keyColumn = "id")
    Set<BookCardEvents> bookCardEvents = new LinkedHashSet<>();

    public Book(String isbNumber, String author, String title) {
        this.isbNumber = isbNumber;
        this.author = author;
        this.title = title;
    }
}
