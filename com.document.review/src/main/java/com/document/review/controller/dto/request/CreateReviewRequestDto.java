package com.document.review.controller.dto.request;

import jakarta.validation.constraints.Size;

public record CreateReviewRequestDto(String username, Long bookId, String content,
                                     // rating의 범위
                                     @Size(min = 1, max = 5)
                                     Integer rating) {

}
