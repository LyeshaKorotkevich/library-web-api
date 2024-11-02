package com.modsen.bookservice.util;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.dto.response.AuthorResponse;
import com.modsen.bookservice.model.Author;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
public class AuthorTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Vladimer";

    @Builder.Default
    private String surname = "Korotkevich";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1930, 11, 26);

    public Author getAuthor() {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        author.setSurname(surname);
        author.setBirthDate(birthDate);
        return author;
    }

    public AuthorRequest getRequest() {
        return new AuthorRequest(name, surname, birthDate);
    }

    public AuthorResponse getResponse() {
        return new AuthorResponse(id, name, surname, birthDate);
    }
}

