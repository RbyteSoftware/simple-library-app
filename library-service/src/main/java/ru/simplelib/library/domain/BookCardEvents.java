package ru.simplelib.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
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
public class BookCardEvents {
    @Id
    private Long id;
    private BookEvent event;
    @CreatedBy
    @Column("createdBy")
    private User createdBy;
    @CreatedDate
    @Column("createdAt")
    private LocalDateTime createdAt;

    public BookCardEvents(BookEvent event) {
        this.event = event;
    }
}
