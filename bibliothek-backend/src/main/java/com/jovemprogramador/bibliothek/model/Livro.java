package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_Livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_livro")
    private long codLivro;
    @NotEmpty
    private String titulo;
    @NotEmpty
    private String genero;
    @NotEmpty
    private String autor;
    @NotEmpty
    private String editora;
    private byte quantidade;
    @NotEmpty
    private String imagemUrl;
    private boolean destaque;
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String descricao;

    public Livro(long cod_livro, @NotEmpty String titulo, @NotEmpty String genero, @NotEmpty String autor, @NotEmpty String editora, byte quantidade, @NotEmpty String imagemUrl, boolean destaque, @NotEmpty String descricao) {
        this.codLivro = cod_livro;
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

    public long getCod_livro() {
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

    public byte getQuantidade() {
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

    public void setCod_livro(long codLivro) {
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

    public void setQuantidade(byte quantidade) {
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
