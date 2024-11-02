package com.modsen.bookservice.service;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.model.Author;

public interface AuthorService {
    Author getOrCreateAuthor(AuthorRequest authorRequest);
}
