package dev.williamnogueira.bibliothek.domain.user.dto;

public record UserRequestDTO(
        String registration,
        String name,
        String password,
        String profilePic) {
}
