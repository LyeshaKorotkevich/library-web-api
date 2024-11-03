package com.modsen.bookservice.mapper;

import com.modsen.bookservice.dto.request.BookRequest;
import com.modsen.bookservice.dto.response.BookResponse;
import com.modsen.bookservice.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {

    BookResponse toResponse(Book book);

    @Mapping(target = "id", ignore = true)
    Book toEntity(BookRequest bookRequest);
}
