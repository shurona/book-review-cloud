package com.document.book.dto;

import java.time.LocalDateTime;

public record BookResponseDto(String title, String description, String author, LocalDateTime publishTime) {

}
