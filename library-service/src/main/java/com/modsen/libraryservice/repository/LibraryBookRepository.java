package com.modsen.libraryservice.repository;

import com.modsen.libraryservice.model.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing {@link LibraryBook} entities.
 *
 * This interface extends {@link JpaRepository} to provide CRUD operations
 * and custom query methods for {@link LibraryBook} objects.
 */
public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {

    /**
     * Retrieves a paginated list of all library books that are currently available (not taken).
     *
     * @param pageable the pagination information, including the page number and size.
     * @return a {@link Page} of {@link LibraryBook} objects that are not currently taken.
     */
    Page<LibraryBook> findAllByTakenAtIsNull(Pageable pageable);
}
