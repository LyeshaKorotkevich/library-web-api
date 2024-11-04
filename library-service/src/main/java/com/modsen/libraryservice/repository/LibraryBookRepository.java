package com.modsen.libraryservice.repository;

import com.modsen.libraryservice.model.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {
    Page<LibraryBook> findAllByTakenAtIsNull(Pageable pageable);
}
