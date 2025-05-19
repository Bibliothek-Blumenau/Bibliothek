package dev.williamnogueira.bibliothek.domain.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BookRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String genre,
        @NotBlank
        String author,
        @NotBlank
        String publisher,
        @NotBlank
        Integer stock,
        @NotBlank
        Integer availableStock,
        @NotBlank
        String coverImage,
        @NotBlank
        String description,
        Boolean featured
) {
}
