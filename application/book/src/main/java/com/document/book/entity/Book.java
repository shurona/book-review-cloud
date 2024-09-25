package com.document.book.entity;

import document.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String description;

    @Column
    private LocalDateTime publishedAt;

    public static Book createBook(String title, String description, String author) {
        Book book = new Book();
        book.title = title;
        book.description = description;
        book.author = author;
        book.publishedAt = LocalDateTime.now();

        return book;
    }

    public void changeDescription(String description) {
        this.description = description;
    }
}
