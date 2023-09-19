package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
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

    private String titulo;
    private Genero genero;
    private String autor;
    private byte quantidade;
    private boolean emprestado;
    private String descricao;
    private String imagemUrl;
    private LocalDateTime diaDeEmprestimo = LocalDateTime.now();

}
