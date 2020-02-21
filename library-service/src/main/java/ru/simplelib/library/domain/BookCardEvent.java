package ru.simplelib.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * The leaf from aggregate object {@see Book}
 *
 * @author Mikhail Yuzbashev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("BookCardEvents")
public class BookCardEvent {
    @Id
    private Long id;
    @Column("bookId")
    private Long bookId;
    private BookEvent event;
    @Column("createdBy")
    private Long createdBy;
    @CreatedDate
    @Column("createdAt")
    private LocalDateTime createdAt;

    public BookCardEvent(Long bookId, BookEvent event, Long createdBy) {
        this.bookId = bookId;
        this.event = event;
        this.createdBy = createdBy;
    }
}
