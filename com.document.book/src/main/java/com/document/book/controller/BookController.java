package com.document.book.controller;

import com.document.book.dto.BookResponseDto;
import com.document.book.dto.CreateBookRequestDto;
import com.document.book.service.BookService;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("books")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Long> createBook(
        @RequestBody CreateBookRequestDto requestDto
    ) {

        Long book = bookService.createBook(requestDto.title(), requestDto.author(),
            requestDto.description());

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookResponseDto> getBookById(
        @PathVariable("id") Long id
    ) {
        BookResponseDto bookById = bookService.getBookById(id);
        return new ResponseEntity<>(bookById, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BookResponseDto> changeDescription(
        @PathVariable("id") Long id,
        @QueryParam("description") String description
    ) {
        BookResponseDto bookInfo = bookService.updateBookDescription(id, description);
        return new ResponseEntity<>(bookInfo, HttpStatus.OK);
    }
}
