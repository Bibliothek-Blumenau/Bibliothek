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
    private int estoque;
    @NotNull
    private int disponibilidade;
    private String imagemUrl;
    private String imagemLivro;
    private boolean destaque;
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String descricao;
    private boolean estadoLivro = false;

	public Livro(long codLivro, @NotEmpty String titulo, @NotEmpty String genero, @NotEmpty String autor, @NotEmpty String editora, @NotNull int estoque, @NotNull int disponibilidade, @NotEmpty String imagemUrl, boolean destaque, @NotEmpty String descricao) {
        this.codLivro = codLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.editora = editora;
        this.estoque = estoque;
        this.disponibilidade = disponibilidade;
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

    public @NotNull int getEstoque() {
        return this.estoque;
    }

    public @NotNull int getDisponibilidade() {
        return this.disponibilidade;
    }

    public String getImagemUrl() {
        return this.imagemUrl;
    }
    
    public String getImagemLivro() {
        return this.imagemLivro;
    }

    public boolean isDestaque() {
        return this.destaque;
    }

    public @NotEmpty String getDescricao() {
        return this.descricao;
    }
    
    public boolean getEstadoLivro() {
		return estadoLivro;
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

    public void setEstoque(@NotNull int estoque) {
        this.estoque = estoque;
    }

    public void setDisponibilidade(@NotNull int disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
    
    public void setImagemLivro(String imagemLivro) {
        this.imagemLivro = imagemLivro;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public void setDescricao(@NotEmpty String descricao) {
        this.descricao = descricao;
    }
    
    public void isLivroUnactive(boolean estadoLivro) {
    	this.estadoLivro = estadoLivro;
    }
}
