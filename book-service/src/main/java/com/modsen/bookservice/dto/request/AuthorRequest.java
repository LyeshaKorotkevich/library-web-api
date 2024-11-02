package com.modsen.bookservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AuthorRequest(
        @NotBlank
        String name,

        @NotBlank
        String surname,

        @NotNull
        LocalDate birthDate
) {

}
