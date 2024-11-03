package com.modsen.libraryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "library_books")
public class LibraryBook {

    @Id
    private Long bookId;

    private LocalDateTime takenAt;
    private LocalDateTime returnBy;
}
