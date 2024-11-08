package com.modsen.bookservice.mapper;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.dto.response.AuthorResponse;
import com.modsen.bookservice.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between Author entities and Data Transfer Objects (DTOs).
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    /**
     * Converts an Author entity to an AuthorResponse DTO.
     *
     * @param author the Author entity to convert
     * @return the converted AuthorResponse DTO
     */
    AuthorResponse toResponse(Author author);

    /**
     * Converts an AuthorRequest DTO to an Author entity.
     * The 'id' field in the Author entity is ignored during mapping,
     * as it is typically generated by the database.
     * The 'books' field is also ignored to prevent initializing
     * unnecessary relationships at this point.
     *
     * @param authorRequest the AuthorRequest DTO to convert
     * @return the converted Author entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorRequest authorRequest);
}
