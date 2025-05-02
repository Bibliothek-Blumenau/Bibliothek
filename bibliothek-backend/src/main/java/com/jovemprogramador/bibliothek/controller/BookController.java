package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.domain.book.BookEntity;
import com.jovemprogramador.bibliothek.domain.book.BookRepository;
import com.jovemprogramador.bibliothek.domain.book.BookService;
import com.jovemprogramador.bibliothek.domain.book.dto.BookResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping
    public Page<BookEntity> findAllReversed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Sort sort = Sort.by(Sort.Order.desc("codLivro"));
        Pageable pageable = PageRequest.of(page, size, sort);

        return bookRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody @Valid BookEntity bookEntity) {
        bookEntity.setDisponibilidade(bookEntity.getEstoque());
        bookRepository.save(bookEntity);
    }

    @PutMapping("/{codLivro}")
    public void update(@Valid @RequestBody BookEntity bookEntity, @PathVariable long codLivro) {
        if (!bookRepository.existsById(codLivro)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.");
        }

        BookEntity bookEntityAtual = bookRepository.findById(codLivro);

        int estoqueAnterior = bookEntityAtual.getEstoque();
        int estoqueNovo = bookEntity.getEstoque();
        int disponibilidadeAtual = bookEntityAtual.getDisponibilidade();

        if (estoqueNovo != estoqueAnterior) {
            int diferencaEstoque = estoqueNovo - estoqueAnterior;
            int novaDisponibilidade = disponibilidadeAtual + diferencaEstoque;
            bookEntity.setDisponibilidade(novaDisponibilidade);
        } else {
            bookEntity.setDisponibilidade(disponibilidadeAtual);
        }

        bookRepository.save(bookEntity);
    }

    @DeleteMapping("/{codLivro}")
    public void delete(@PathVariable long codLivro) {
        bookRepository.deleteById(codLivro);
    }

    @GetMapping("/destaque")
    public List<BookEntity> getLivrosEmDestaque() {
        List<BookEntity> livrosEmDestaque = bookRepository.findByDestaqueIsTrue();
        if (livrosEmDestaque.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum livro em destaque encontrado.");
        }
        return livrosEmDestaque;
    }

    @GetMapping("/busca")
    public ResponseEntity<List<BookEntity>> buscarLivrosPorTipo(
            @RequestParam(name = "titulo", required = false) String titulo,
            @RequestParam(name = "tipo", required = false) String tipo) {

        if (titulo != null && tipo != null) {
            titulo = "%" + titulo + "%";

            if (tipo.equals("titulo")) {
                List<BookEntity> livrosList = bookRepository.findAllByTituloLike(titulo);
                return ResponseEntity.ok(livrosList);
            } else if (tipo.equals("autor")) {
                List<BookEntity> livrosList = bookRepository.findAllByAutorLike(titulo);
                return ResponseEntity.ok(livrosList);
            } else if (tipo.equals("genero")) {
                List<BookEntity> livrosList = bookRepository.findAllByGeneroLike(titulo);
                return ResponseEntity.ok(livrosList);
            }
        }

        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/recomendacoes")
    public ResponseEntity<List<BookEntity>> buscarLivrosRecomendadosPorGenero(
            @RequestParam(name = "genero") String genero) {
        List<BookEntity> recomendacoes = bookRepository.findAllByGeneroLike(genero);
        return ResponseEntity.ok(recomendacoes);
    }
}
