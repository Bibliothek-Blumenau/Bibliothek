package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.model.Livro;
import com.jovemprogramador.bibliothek.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin
public class LivroController {

    @Autowired
    private LivroRepository repository;

    @GetMapping
    public List<Livro> findAll() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Livro livro) {
        repository.save(livro);
    }

    @PutMapping("/{cod_livro}")
    public void update(@Valid @RequestBody Livro livro, @PathVariable long cod_livro) {
        if (!repository.existsById(cod_livro)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.");
        }
        repository.save(livro);
    }

    @DeleteMapping("/{cod_livro}")
    public void delete(@Valid @RequestBody Livro livro, @PathVariable long cod_livro) {
        repository.deleteById(cod_livro);
    }
}
