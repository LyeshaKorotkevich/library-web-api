package com.modsen.bookservice.mapper;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.dto.response.AuthorResponse;
import com.modsen.bookservice.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponse toResponse(Author author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorRequest authorRequest);
}
