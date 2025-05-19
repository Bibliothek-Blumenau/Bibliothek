package dev.williamnogueira.bibliothek.controller;

import dev.williamnogueira.bibliothek.domain.book.BookRepository;
import dev.williamnogueira.bibliothek.domain.book.BookService;
import dev.williamnogueira.bibliothek.domain.book.dto.BookRequestDTO;
import dev.williamnogueira.bibliothek.domain.book.dto.BookResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> findAllPaginated(Pageable pageable) {
        return ResponseEntity.ok(bookService.findAllPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookRequestDTO book) {
        var createdBook = bookService.create(book);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBook.id())
                .toUri();

        return ResponseEntity.created(location).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@Valid @RequestBody BookRequestDTO book, @PathVariable String id) {
        return ResponseEntity.ok(bookService.updateById(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/featured")
    public ResponseEntity<List<BookResponseDTO>> getFeaturedBooks() {
        return ResponseEntity.ok(bookService.getFeaturedBooks());
    }

//    @GetMapping("/busca")
//    public ResponseEntity<List<BookEntity>> buscarLivrosPorTipo(
//            @RequestParam(name = "titulo", required = false) String titulo,
//            @RequestParam(name = "tipo", required = false) String tipo) {
//
//        if (titulo != null && tipo != null) {
//            titulo = "%" + titulo + "%";
//
//            if (tipo.equals("titulo")) {
//                List<BookEntity> livrosList = bookRepository.findAllByTituloLike(titulo);
//                return ResponseEntity.ok(livrosList);
//            } else if (tipo.equals("autor")) {
//                List<BookEntity> livrosList = bookRepository.findAllByAutorLike(titulo);
//                return ResponseEntity.ok(livrosList);
//            } else if (tipo.equals("genero")) {
//                List<BookEntity> livrosList = bookRepository.findAllByGeneroLike(titulo);
//                return ResponseEntity.ok(livrosList);
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }

//    @GetMapping("/recomendacoes")
//    public ResponseEntity<List<BookEntity>> buscarLivrosRecomendadosPorGenero(
//            @RequestParam(name = "genero") String genero) {
//        List<BookEntity> recomendacoes = bookRepository.findAllByGeneroLike(genero);
//        return ResponseEntity.ok(recomendacoes);
//    }
}
