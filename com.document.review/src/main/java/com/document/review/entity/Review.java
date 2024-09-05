package com.document.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Long bookId;

    @Column
    private String username;

    @Column
    private String content;

    @Column
    private Integer rating;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

    // private 생성자도 좋아보임
    public static Review createReview(Long bookId, String username, String content,
        Integer rating) {

        Review review = new Review();
        review.bookId = bookId;
        review.username = username;
        review.content = content;
        review.rating = rating;

        return review;

    }

    /**
     * 비즈니스 로직
     */
    private boolean checkRating(Integer rating) {

        if (rating > 0 && rating < 6) {
            return true;
        }

        return false;
    }

}
