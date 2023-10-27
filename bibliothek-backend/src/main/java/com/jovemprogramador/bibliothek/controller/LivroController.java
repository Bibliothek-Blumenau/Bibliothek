package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.model.Livro;
import com.jovemprogramador.bibliothek.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping
    public Page<Livro> findAllReversed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Sort sort = Sort.by(Sort.Order.desc("codLivro"));
        Pageable pageable = PageRequest.of(page, size, sort);

        return livroRepository.findAll(pageable);
    }

    @GetMapping("/{codLivro}")
    public Livro findById(@PathVariable long codLivro) {
        return livroRepository.findById(codLivro);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody @Valid Livro livro) {
        livroRepository.save(livro);
    }

    @PutMapping("/{codLivro}")
    public void update(@Valid @RequestBody Livro livro, @PathVariable long codLivro) {
        if (!livroRepository.existsById(codLivro)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.");
        }
        livroRepository.save(livro);
    }

    @DeleteMapping("/{codLivro}")
    public void delete(@PathVariable long codLivro) {
        livroRepository.deleteById(codLivro);
    }

    @GetMapping("/destaque")
    public List<Livro> getLivrosEmDestaque() {
        List<Livro> livrosEmDestaque = livroRepository.findByDestaqueIsTrue();
        if (livrosEmDestaque.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No highlighted books found.");
        }
        return livrosEmDestaque;
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Livro>> buscarLivrosPorTipo(
            @RequestParam(name = "titulo", required = false) String titulo,
            @RequestParam(name = "tipo", required = false) String tipo) {

        if (titulo != null && tipo != null) {
            titulo = "%" + titulo + "%";

            if (tipo.equals("titulo")) {
                List<Livro> livrosList = livroRepository.findAllByTituloLike(titulo);
                return ResponseEntity.ok(livrosList);
            } else if (tipo.equals("autor")) {
                List<Livro> livrosList = livroRepository.findAllByAutorLike(titulo);
                return ResponseEntity.ok(livrosList);
            } else if (tipo.equals("genero")) {
                List<Livro> livrosList = livroRepository.findAllByGeneroLike(titulo);
                return ResponseEntity.ok(livrosList);
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
