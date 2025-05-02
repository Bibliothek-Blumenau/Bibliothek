package com.jovemprogramador.bibliothek.domain.book.dto;

public record BookResponseDTO(
    String title,
    String genre,
    String author,
    String publisher,
    Integer stock,
    Integer availableStock,
    String coverImage,
    String description,
    Boolean featured
) {
}
