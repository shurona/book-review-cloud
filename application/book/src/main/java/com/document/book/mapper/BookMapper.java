package com.document.book.mapper;

import com.document.book.dto.BookResponseDto;
import com.document.book.entity.Book;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(target = "publishTime", source = "publishedAt")
    BookResponseDto bookToDto(Book book);
}
