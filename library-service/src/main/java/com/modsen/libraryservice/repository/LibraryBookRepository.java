package com.modsen.libraryservice.repository;

import com.modsen.libraryservice.model.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {
    List<LibraryBook> findAllByTakenAtIsNull();
}
