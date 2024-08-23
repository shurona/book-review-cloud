package com.document.book.service;

import com.document.book.dto.BookResponseDto;
import com.document.book.entity.Book;
import com.document.book.mapper.BookMapper;
import com.document.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public Long createBook(String title, String author, String description) {
        Book book = Book.createBook(title, author, description);
        bookRepository.save(book);
        return book.getId();
    }

    public BookResponseDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        return bookMapper.bookToDto(book);
    }

    @Transactional
    public BookResponseDto updateBookDescription(Long bookId, String description) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        book.changeDescription(description);
        return bookMapper.bookToDto(book);
    }

}
