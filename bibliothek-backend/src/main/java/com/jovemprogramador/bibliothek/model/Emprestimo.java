package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_Livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cod_emprestimo;
    @OneToOne
    private Livro cod_livro;
    @OneToOne
    private User matricula;
    @Column(name = "data_emprestimo", columnDefinition = "DATE")
    private LocalDateTime dataEmprestimo = LocalDateTime.now();
    @Column(name = "data_entrega", columnDefinition = "DATE")
    private LocalDateTime dataEntrega = dataEmprestimo.plus(Duration.ofDays(14));
    private BigDecimal multa;

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
