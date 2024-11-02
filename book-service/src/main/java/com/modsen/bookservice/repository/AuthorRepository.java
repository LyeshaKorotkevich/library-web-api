package com.modsen.bookservice.repository;

import com.modsen.bookservice.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameAndSurnameAndBirthDate(String name, String surname, LocalDate birthDate);
}
