package com.jovemprogramador.bibliothek.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jovemprogramador.bibliothek.model.Livro;
import com.jovemprogramador.bibliothek.repository.LivroRepository;

import jakarta.validation.Valid;

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

        return livroRepository.findByEstadoLivro(pageable);
    }

    @GetMapping("/{codLivro}")
    public Livro findById(@PathVariable long codLivro) {
        return livroRepository.findById(codLivro);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid Livro livro) {
        livro.setDisponibilidade(livro.getEstoque());
        livroRepository.save(livro);

        // Obtém o codLivro após o cadastro
        long codLivro = livro.getCodLivro();

        // Retorna o codLivro na resposta
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codLivro}").buildAndExpand(codLivro).toUri();
        return ResponseEntity.created(location).build();
    }


    @PutMapping("/{codLivro}")
    public void update(@Valid @RequestBody Livro livro, @PathVariable long codLivro) {
        if (!livroRepository.existsById(codLivro)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.");
        }

        Livro livroAtual = livroRepository.findById(codLivro);

        int estoqueAnterior = livroAtual.getEstoque();
        int estoqueNovo = livro.getEstoque();
        int disponibilidadeAtual = livroAtual.getDisponibilidade();

        if (estoqueNovo != estoqueAnterior) {
            int diferencaEstoque = estoqueNovo - estoqueAnterior;
            int novaDisponibilidade = disponibilidadeAtual + diferencaEstoque;
            livro.setDisponibilidade(novaDisponibilidade);
        } else {
            livro.setDisponibilidade(disponibilidadeAtual);
        }

        livroRepository.save(livro);
    }

    @DeleteMapping("/{codLivro}")
    public void delete(@PathVariable long codLivro) {
        Livro estadoLivro = livroRepository.findById(codLivro);
        
        estadoLivro.isLivroUnactive(true);
        livroRepository.save(estadoLivro);
    }

    @GetMapping("/destaque")
    public List<Livro> getLivrosEmDestaque() {
        List<Livro> livrosEmDestaque = livroRepository.findByDestaque();
        if (livrosEmDestaque.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum livro em destaque encontrado.");
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
                List<Livro> livrosList = livroRepository.findByTitulo(titulo);
                return ResponseEntity.ok(livrosList);
            } else if (tipo.equals("autor")) {
                List<Livro> livrosList = livroRepository.findByAutor(titulo);
                return ResponseEntity.ok(livrosList);
            } else if (tipo.equals("genero")) {
                List<Livro> livrosList = livroRepository.findByGenero(titulo);
                return ResponseEntity.ok(livrosList);
            }
        }

        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/recomendacoes")
    public ResponseEntity<List<Livro>> buscarLivrosRecomendadosPorGenero(
            @RequestParam(name = "genero") String genero) {
        List<Livro> recomendacoes = livroRepository.findByGenero(genero);
        return ResponseEntity.ok(recomendacoes);
    }
}
