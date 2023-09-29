package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_Emprestimo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cod_emprestimo;

    @Column(name = "data_emprestimo", columnDefinition = "DATE")
    private LocalDateTime dataEmprestimo = LocalDateTime.now();

    @Column(name = "data_entrega", columnDefinition = "DATE")
    private LocalDateTime dataEntrega = dataEmprestimo.plus(Duration.ofDays(14));

    private BigDecimal multa;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "TB_EmprestimoLivro",
            joinColumns = @JoinColumn(name = "emprestimo_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livros;

    public BigDecimal calcularMulta() {
        LocalDateTime dataAtual = LocalDateTime.now();

        if (dataAtual.isAfter(dataEntrega)) {
            long diasAtraso = Duration.between(dataEntrega, dataAtual).toDays();
            BigDecimal multaPorDia = new BigDecimal("0.15");
            multa = multaPorDia.multiply(BigDecimal.valueOf(diasAtraso));
        } else {
            multa = BigDecimal.ZERO;
        }

        return multa;
    }
}