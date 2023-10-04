package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_Emprestimo")
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

    public Emprestimo(long cod_emprestimo, LocalDateTime dataEmprestimo, LocalDateTime dataEntrega, BigDecimal multa, User user, List<Livro> livros) {
        this.cod_emprestimo = cod_emprestimo;
        this.dataEmprestimo = dataEmprestimo;
        this.dataEntrega = dataEntrega;
        this.multa = multa;
        this.user = user;
        this.livros = livros;
    }

    public Emprestimo() {
    }

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

    public long getCod_emprestimo() {
        return this.cod_emprestimo;
    }

    public LocalDateTime getDataEmprestimo() {
        return this.dataEmprestimo;
    }

    public LocalDateTime getDataEntrega() {
        return this.dataEntrega;
    }

    public BigDecimal getMulta() {
        return this.multa;
    }

    public User getUser() {
        return this.user;
    }

    public List<Livro> getLivros() {
        return this.livros;
    }

    public void setCod_emprestimo(long cod_emprestimo) {
        this.cod_emprestimo = cod_emprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}