package com.document.review.endpoint;

import com.document.review.endpoint.dto.BookResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "book-service")
@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/books/{id}")
    BookResponseDto findBookById(@RequestParam(name = "id") Long id);


}
