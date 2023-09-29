package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_User")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID userId;
    private String matricula;
    private String nomeCompleto;
    private String password;
    private String roles;

    @OneToMany(mappedBy = "user")
    private List<Emprestimo> emprestimos;

    public User(String matricula, String nomeCompleto, String password, String roles) {
        this.matricula = matricula;
        this.nomeCompleto = nomeCompleto;
        this.password = password;
        this.roles = roles;
    }
}