package dev.williamnogueira.bibliothek.controller;

import dev.williamnogueira.bibliothek.domain.loan.LoanEntity;
import dev.williamnogueira.bibliothek.domain.user.UserEntity;
import dev.williamnogueira.bibliothek.domain.loan.LoanRepository;
import dev.williamnogueira.bibliothek.domain.book.BookRepository;
import dev.williamnogueira.bibliothek.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<LoanEntity>> getTodosEmprestimos() {
        Sort sort = Sort.by(Sort.Order.desc("id"));

        List<LoanEntity> loanEntities = loanRepository.findAll(sort);

        return ResponseEntity.ok(loanEntities);
    }

    @GetMapping("/verificar/{matricula}")
    public ResponseEntity<List<LoanEntity>> getEmprestimosUsuario(@PathVariable String matricula) {
        UserEntity usuario = userRepository.findById(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Sort sort = Sort.by(Sort.Order.desc("id"));

        List<LoanEntity> loanEntities = loanRepository.findByUser(usuario, sort);

        return ResponseEntity.ok(loanEntities);
    }

//    @PostMapping("/solicitar/{codLivro}/{matricula}")
//    public ResponseEntity<String> solicitarLivro(@PathVariable long codLivro, @PathVariable String matricula) {
//        BookEntity bookEntity = bookRepository.findById(codLivro);
//
//        if (bookEntity.getDisponibilidade() <= 0) {
//            return ResponseEntity.badRequest().body("Livro não disponível para empréstimo");
//        }
//
//        UserEntity usuario = userRepository.findById(matricula).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
//
//        LoanEntity loanEntity = new LoanEntity();
//        loanEntity.setLivro(bookEntity);
//        loanEntity.setUsuario(usuario);
//        loanEntity.setStatus("Pendente");
//        loanEntity.setDataRequisicao(LocalDateTime.now());
//        loanEntity.setMulta(BigDecimal.valueOf(0));
//
//        bookEntity.setDisponibilidade(bookEntity.getDisponibilidade() - 1);
//        bookRepository.save(bookEntity);
//
//        loanRepository.save(loanEntity);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/emprestimo/{codEmprestimo}")
//    public ResponseEntity<String> emprestimo(@PathVariable long codEmprestimo) {
//        LoanEntity loanEntity = loanRepository.findById(codEmprestimo)
//                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));
//
//        if (!loanEntity.getStatus().equals("Pendente")) {
//            return ResponseEntity.badRequest().body("Este empréstimo não está pendente.");
//        }
//
//        loanEntity.setDataEmprestimo(LocalDateTime.now());
//        loanEntity.setDataEntrega(loanEntity.getDataEmprestimo().plusDays(14));
//        loanEntity.setStatus("Emprestado");
//
//        loanRepository.save(loanEntity);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/finalizar/{codEmprestimo}")
//    public ResponseEntity<String> finalizarEmprestimo(@PathVariable long codEmprestimo) {
//        LoanEntity loanEntity = loanRepository.findById(codEmprestimo)
//                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));
//
//        if (loanEntity.getStatus().isBlank()) {
//            return ResponseEntity.badRequest().body("Este empréstimo não está em andamento.");
//        }
//
//        BookEntity bookEntity = loanEntity.getLivro();
//        bookEntity.setDisponibilidade(bookEntity.getDisponibilidade() + 1);
//        bookRepository.save(bookEntity);
//
//        LocalDateTime dataAtual = LocalDateTime.now();
//        if (dataAtual.isAfter(loanEntity.getDataEntrega())) {
//            long diasAtraso = Duration.between(loanEntity.getDataEntrega(), dataAtual).toDays();
//            BigDecimal multaPorDia = new BigDecimal("1");
//            loanEntity.setMulta((multaPorDia.multiply(BigDecimal.valueOf(diasAtraso))));
//        } else {
//            loanEntity.setMulta(BigDecimal.ZERO);
//        }
//
//        loanEntity.setStatus("Finalizado");
//        loanRepository.save(loanEntity);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/emprestimo/{codEmprestimo}")  //Working
//    public ResponseEntity<String> renovarEmprestimo(@PathVariable long codEmprestimo) {
//        LoanEntity loanEntity = loanRepository.findById(codEmprestimo)
//                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));
//
//        if (loanEntity.getStatus().equals("Pendente")) {
//            return ResponseEntity.badRequest().body("Este empréstimo não está pendente.");
//        }else if(loanEntity.getStatus().equals("Finalizado")) {
//            return ResponseEntity.badRequest().body("Este empréstimo está Finalizado.");
//        }
//        loanEntity.setDataEntrega(loanEntity.getDataEntrega().plusDays(7));
//        loanEntity.setStatus("Renovado");
//        loanRepository.save(loanEntity);
//
//        return ResponseEntity.ok().build();
//    }

}