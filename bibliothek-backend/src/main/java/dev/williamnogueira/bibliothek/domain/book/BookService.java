package dev.williamnogueira.bibliothek.domain.book;

import dev.williamnogueira.bibliothek.domain.book.dto.BookRequestDTO;
import dev.williamnogueira.bibliothek.domain.book.dto.BookResponseDTO;
import dev.williamnogueira.bibliothek.domain.book.exception.BookNotFoundException;
import dev.williamnogueira.bibliothek.domain.book.mapper.BookMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public BookResponseDTO findById(String id) {
        var book = getEntity(UUID.fromString(id));
        return BookMapper.toDto(book);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> findAllWithFilter(String searchQuery, Pageable pageable) {
        return bookRepository.filterBooks(searchQuery, pageable).map(BookMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> getFeaturedBooks() {
        List<BookEntity> featuredBooks = bookRepository.findByFeaturedIsTrue();
        if (featuredBooks.isEmpty()) {
            throw new BookNotFoundException("No featured books found.");
        }
        return featuredBooks.stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> getRecommendationsByGenre(String genre) {
        var limitFive = PageRequest.of(0, 5);
        return bookRepository.getRecommendationsByGenre(genre, limitFive)
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Transactional
    public BookResponseDTO create(BookRequestDTO book) {
        return BookMapper.toDto(bookRepository.save(BookMapper.toEntity(book)));
    }

    @Transactional
    public BookResponseDTO updateById(String id, @Valid BookRequestDTO book) {
        var bookToUpdate = getEntity(UUID.fromString(id));
        var bookToSave = BookMapper.toEntity(book);

        if (!Objects.equals(book.stock(), bookToUpdate.getStock())) {
            int stockDifference = book.stock() - bookToUpdate.getStock();
            int newAvailability = bookToUpdate.getAvailableStock() + stockDifference;
            bookToSave.setAvailableStock(newAvailability);
        }

        return BookMapper.toDto(bookRepository.save(bookToSave));
    }

    @Transactional
    public void deleteById(String id) {
        var book = getEntity(UUID.fromString(id));
        bookRepository.delete(book);
    }

    public BookEntity getEntity(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }
}
