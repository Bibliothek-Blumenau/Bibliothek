package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.model.Livro;
import com.jovemprogramador.bibliothek.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Livro> findAll() {
        return livroRepository.findAll();
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{cod_livro}")
    public Livro findById(@PathVariable long cod_livro) {
        return livroRepository.findById(cod_livro);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Livro livro) {
        livroRepository.save(livro);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{cod_livro}")
    public void update(@Valid @RequestBody Livro livro, @PathVariable long cod_livro) {
        if (!livroRepository.existsById(cod_livro)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.");
        }
        livroRepository.save(livro);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{cod_livro}")
    public void delete(@Valid @RequestBody Livro livro, @PathVariable long cod_livro) {
        livroRepository.deleteById(cod_livro);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/destaque")
    public List<Livro> getLivrosEmDestaque() {
        List<Livro> livrosEmDestaque = livroRepository.findByDestaqueIsTrue();
        if (livrosEmDestaque.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No highlighted books found.");
        }
        return livrosEmDestaque;
    }

    @PreAuthorize("hasRole('USER')")
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
