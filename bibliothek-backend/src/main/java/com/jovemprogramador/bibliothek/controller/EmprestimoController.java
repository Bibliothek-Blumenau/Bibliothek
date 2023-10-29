package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.model.Emprestimo;
import com.jovemprogramador.bibliothek.model.Livro;
import com.jovemprogramador.bibliothek.model.User;
import com.jovemprogramador.bibliothek.repository.EmprestimoRepository;
import com.jovemprogramador.bibliothek.repository.LivroRepository;
import com.jovemprogramador.bibliothek.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Emprestimo> > getTodosEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/verificar/{matricula}")
    public ResponseEntity<List<Emprestimo>> getEmprestimosUsuario(@PathVariable String matricula) {
        User usuario = userRepository.findById(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuario(usuario);

        return ResponseEntity.ok(emprestimos);
    }

    @PostMapping("/solicitar/{codLivro}/{matricula}")
    public ResponseEntity<String> solicitarLivro(@PathVariable long codLivro, @PathVariable String matricula) {
        Livro livro = livroRepository.findById(codLivro);

        if (livro.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("Livro não disponível para empréstimo");
        }

        User usuario = userRepository.findById(matricula).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setStatus("Pendente");

        livro.setQuantidade(livro.getQuantidade() - 1);
        livroRepository.save(livro);

        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/emprestimo/{codEmprestimo}")
    public ResponseEntity<String> emprestimo(@PathVariable long codEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.findById(codEmprestimo)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (!emprestimo.getStatus().equals("Pendente")) {
            return ResponseEntity.badRequest().body("Este empréstimo não está pendente.");
        }

        emprestimo.setDataEmprestimo(LocalDateTime.now());
        emprestimo.setDataEntrega(emprestimo.getDataEmprestimo().plusDays(14));
        emprestimo.setMulta(BigDecimal.valueOf(0));
        emprestimo.setStatus("Emprestado");

        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/finalizar/{codEmprestimo}")
    public ResponseEntity<String> finalizarEmprestimo(@PathVariable long codEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.findById(codEmprestimo)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (!emprestimo.getStatus().equals("Emprestado")) {
            return ResponseEntity.badRequest().body("Este empréstimo não está em andamento.");
        }

        Livro livro = emprestimo.getLivro();
        livro.setQuantidade(livro.getQuantidade() + 1);
        livroRepository.save(livro);

        emprestimo.setMulta(BigDecimal.valueOf(0));
        emprestimo.setStatus("Finalizado");
        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok().build();
    }

}