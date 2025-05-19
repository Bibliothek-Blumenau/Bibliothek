package dev.williamnogueira.bibliothek.domain.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {
    public BookNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
