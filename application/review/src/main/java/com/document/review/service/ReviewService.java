package com.document.review.service;

import com.document.review.controller.dto.request.CreateReviewRequestDto;
import com.document.review.endpoint.BookClient;
import com.document.review.endpoint.dto.BookResponseDto;
import com.document.review.entity.Review;
import com.document.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookClient bookClient;

    @Transactional
    public Long createReview(CreateReviewRequestDto requestDto) {

        // Book 으로 전달해서 확인해보기
        BookResponseDto bookById = bookClient.findBookById(requestDto.bookId());
        System.out.println(bookById);

        Review save = reviewRepository.save(
            Review.createReview(requestDto.bookId(), requestDto.username(), requestDto.content(),
                requestDto.rating()));

        return save.getId();
    }


}
