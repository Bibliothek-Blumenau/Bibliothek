package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.model.Emprestimo;
import com.jovemprogramador.bibliothek.model.Livro;
import com.jovemprogramador.bibliothek.model.User;
import com.jovemprogramador.bibliothek.repository.EmprestimoRepository;
import com.jovemprogramador.bibliothek.repository.LivroRepository;
import com.jovemprogramador.bibliothek.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
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
    public ResponseEntity<List<Emprestimo>> getTodosEmprestimos() {
        Sort sort = Sort.by(Sort.Order.desc("codEmprestimo"));

        List<Emprestimo> emprestimos = emprestimoRepository.findAll(sort);

        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/verificar/{matricula}")
    public ResponseEntity<List<Emprestimo>> getEmprestimosUsuario(@PathVariable String matricula) {
        User usuario = userRepository.findById(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Sort sort = Sort.by(Sort.Order.desc("codEmprestimo"));

        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuario(usuario, sort);

        return ResponseEntity.ok(emprestimos);
    }

    @PostMapping("/solicitar/{codLivro}/{matricula}")
    public ResponseEntity<String> solicitarLivro(@PathVariable long codLivro, @PathVariable String matricula) {
        Livro livro = livroRepository.findById(codLivro);

        if (livro.getDisponibilidade() <= 0) {
            return ResponseEntity.badRequest().body("Livro não disponível para empréstimo");
        }

        User usuario = userRepository.findById(matricula).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setStatus("Pendente");
        emprestimo.setDataRequisicao(LocalDateTime.now());
        emprestimo.setMulta(BigDecimal.valueOf(0));

        livro.setDisponibilidade(livro.getDisponibilidade() - 1);
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
        emprestimo.setStatus("Emprestado");

        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/finalizar/{codEmprestimo}")
    public ResponseEntity<String> finalizarEmprestimo(@PathVariable long codEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.findById(codEmprestimo)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (emprestimo.getStatus().isBlank()) {
            return ResponseEntity.badRequest().body("Este empréstimo não está em andamento.");
        }

        Livro livro = emprestimo.getLivro();
        livro.setDisponibilidade(livro.getDisponibilidade() + 1);
        livroRepository.save(livro);

        LocalDateTime dataAtual = LocalDateTime.now();
        if (dataAtual.isAfter(emprestimo.getDataEntrega())) {
            long diasAtraso = Duration.between(emprestimo.getDataEntrega(), dataAtual).toDays();
            BigDecimal multaPorDia = new BigDecimal("1");
            emprestimo.setMulta((multaPorDia.multiply(BigDecimal.valueOf(diasAtraso))));
        } else {
            emprestimo.setMulta(BigDecimal.ZERO);
        }

        emprestimo.setStatus("Finalizado");
        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/emprestimo/{codEmprestimo}")  //Working
    public ResponseEntity<String> renovarEmprestimo(@PathVariable long codEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.findById(codEmprestimo)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));

        if (emprestimo.getStatus().equals("Pendente")) {
            return ResponseEntity.badRequest().body("Este empréstimo não está pendente.");
        }else if(emprestimo.getStatus().equals("Finalizado")) {
            return ResponseEntity.badRequest().body("Este empréstimo está Finalizado.");
        }
        emprestimo.setDataEntrega(emprestimo.getDataEntrega().plusDays(7));
        emprestimo.setStatus("Renovado");
        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok().build();
    }

}