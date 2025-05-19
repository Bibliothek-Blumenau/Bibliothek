package dev.williamnogueira.bibliothek.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID>{
    List<BookEntity> findByFeaturedIsTrue();
//    List<BookEntity> findAllByTituloLike(String titulo);
//    List<BookEntity> findAllByAutorLike(String titulo);
//    List<BookEntity> findAllByGeneroLike(String titulo);
}
