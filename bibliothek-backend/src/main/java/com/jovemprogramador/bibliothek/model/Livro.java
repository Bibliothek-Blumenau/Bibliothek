package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_Livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cod_livro;
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
    @Column(columnDefinition="TEXT")
    @NotEmpty
    private String descricao;
}
