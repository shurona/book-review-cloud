package com.document.review.controller;

import com.document.review.controller.dto.request.CreateReviewRequestDto;
import com.document.review.service.ReviewService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Long> createReview(@RequestBody CreateReviewRequestDto requestDto) {

        System.out.println(requestDto);
        Long review = reviewService.createReview(requestDto);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }


    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignExceptionException(
        FeignException exception) {

        return ResponseEntity.ok(exception.getMessage());
    }
}
