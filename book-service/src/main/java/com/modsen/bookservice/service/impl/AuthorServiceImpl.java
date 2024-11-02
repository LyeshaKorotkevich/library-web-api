package com.modsen.bookservice.service.impl;

import com.modsen.bookservice.dto.request.AuthorRequest;
import com.modsen.bookservice.mapper.AuthorMapper;
import com.modsen.bookservice.model.Author;
import com.modsen.bookservice.repository.AuthorRepository;
import com.modsen.bookservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Author getOrCreateAuthor(AuthorRequest authorRequest) {
        return authorRepository.findByNameAndSurnameAndBirthDate(
                        authorRequest.name(),
                        authorRequest.surname(),
                        authorRequest.birthDate())
                .orElseGet(() -> {
                    Author newAuthor = authorMapper.toEntity(authorRequest);
                    return authorRepository.save(newAuthor);
                });
    }
}
