package com.modsen.bookservice.service;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.model.Author;

/**
 * Service interface for managing authors in the book service application.
 * Provides methods for author-related operations.
 */
public interface AuthorService {

    /**
     * Retrieves an existing author or creates a new one based on the provided request.
     *
     * @param authorRequest the request containing author details
     * @return the existing or newly created Author entity
     */
    Author getOrCreateAuthor(AuthorRequest authorRequest);
}
