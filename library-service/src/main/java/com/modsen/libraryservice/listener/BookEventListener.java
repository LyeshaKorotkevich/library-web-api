package com.modsen.libraryservice.listener;

import com.modsen.libraryservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listens for book-related events from a Kafka topic and processes them accordingly.
 *
 * This component is responsible for receiving messages about book events (such as new books)
 * and invoking the appropriate methods in the {@link LibraryService} to handle these events.
 */
@Component
@RequiredArgsConstructor
public class BookEventListener {

    private final LibraryService libraryService;

    /**
     * Listens for book events from the Kafka topic "book-topic".
     *
     * @param bookId the ID of the book received in the event message.
     * This method will add the corresponding library book using the {@link LibraryService}.
     */
    @KafkaListener(topics = "book-topic", groupId = "book-service-group")
    public void listen(Long bookId) {
        libraryService.addLibraryBook(bookId);
    }
}
