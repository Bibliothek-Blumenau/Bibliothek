package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_Emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codEmprestimo;

    @ManyToOne
    @JoinColumn(name = "codLivro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "matricula")
    private User usuario;

    private String status;

    @Column(columnDefinition = "DATE")
    private LocalDateTime dataEmprestimo; //LocalDateTime.now();

    @Column(columnDefinition = "DATE")
    private LocalDateTime dataEntrega; //dataEmprestimo.plus(Duration.ofDays(14));

    private BigDecimal multa;

    public Emprestimo(long codEmprestimo, Livro livro, User usuario, String status, LocalDateTime dataEmprestimo, LocalDateTime dataEntrega, BigDecimal multa) {
        this.codEmprestimo = codEmprestimo;
        this.livro = livro;
        this.usuario = usuario;
        this.status = status;
        this.dataEmprestimo = dataEmprestimo;
        this.dataEntrega = dataEntrega;
        this.multa = multa;
    }

    public Emprestimo() {
    }

    public long getCodEmprestimo() {
        return this.codEmprestimo;
    }

    public Livro getLivro() {
        return this.livro;
    }

    public User getUsuario() {
        return this.usuario;
    }

    public String getStatus() {
        return this.status;
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

    public void setCodEmprestimo(long codEmprestimo) {
        this.codEmprestimo = codEmprestimo;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public void setStatus(String status) {
        this.status = status;
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

/*
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
    } */
}