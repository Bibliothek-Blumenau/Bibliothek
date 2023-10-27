package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TB_Livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codLivro")
    private long codLivro;
    @NotEmpty
    private String titulo;
    @NotEmpty
    private String genero;
    @NotEmpty
    private String autor;
    @NotEmpty
    private String editora;
    @NotNull
    private int quantidade;
    @NotEmpty
    private String imagemUrl;
    private boolean destaque;
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String descricao;

    public Livro(long codLivro, @NotEmpty String titulo, @NotEmpty String genero, @NotEmpty String autor, @NotEmpty String editora, @NotNull int quantidade, @NotEmpty String imagemUrl, boolean destaque, @NotEmpty String descricao) {
        this.codLivro = codLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.editora = editora;
        this.quantidade = quantidade;
        this.imagemUrl = imagemUrl;
        this.destaque = destaque;
        this.descricao = descricao;
    }

    public Livro() {
    }

    public long getCodLivro() {
        return this.codLivro;
    }

    public @NotEmpty String getTitulo() {
        return this.titulo;
    }

    public @NotEmpty String getGenero() {
        return this.genero;
    }

    public @NotEmpty String getAutor() {
        return this.autor;
    }

    public @NotEmpty String getEditora() {
        return this.editora;
    }

    public @NotNull int getQuantidade() {
        return this.quantidade;
    }

    public @NotEmpty String getImagemUrl() {
        return this.imagemUrl;
    }

    public boolean isDestaque() {
        return this.destaque;
    }

    public @NotEmpty String getDescricao() {
        return this.descricao;
    }

    public void setCodLivro(long codLivro) {
        this.codLivro = codLivro;
    }

    public void setTitulo(@NotEmpty String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(@NotEmpty String genero) {
        this.genero = genero;
    }

    public void setAutor(@NotEmpty String autor) {
        this.autor = autor;
    }

    public void setEditora(@NotEmpty String editora) {
        this.editora = editora;
    }

    public void setQuantidade(@NotNull int quantidade) {
        this.quantidade = quantidade;
    }

    public void setImagemUrl(@NotEmpty String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public void setDescricao(@NotEmpty String descricao) {
        this.descricao = descricao;
    }
}
