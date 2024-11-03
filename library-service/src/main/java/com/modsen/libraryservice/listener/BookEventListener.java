package com.modsen.libraryservice.listener;

import com.modsen.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookEventListener {

    private final LibraryService libraryService;

    @KafkaListener(topics = "book-topic", groupId = "book-service-group")
    public void listen(Long bookId) {
        libraryService.addLibraryBook(bookId);
    }
}
