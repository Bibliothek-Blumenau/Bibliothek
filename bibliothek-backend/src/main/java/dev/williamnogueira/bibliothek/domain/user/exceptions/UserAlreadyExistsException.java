package dev.williamnogueira.bibliothek.domain.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {
    public UserAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
