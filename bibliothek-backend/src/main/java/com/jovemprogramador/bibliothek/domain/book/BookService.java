package com.jovemprogramador.bibliothek.domain.book;

import com.jovemprogramador.bibliothek.domain.book.dto.BookResponseDTO;
import com.jovemprogramador.bibliothek.domain.book.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookResponseDTO findById(String id) {
        var book = getEntity(UUID.fromString(id));
        return new BookResponseDTO(
                book.getTitle(),
                book.getGenre(),
                book.getAuthor(),
                book.getPublisher(),
                book.getStock(),
                book.getAvailableStock(),
                book.getCoverImage(),
                book.getDescription(),
                book.getFeatured());
    }

    public BookEntity getEntity(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }
}
