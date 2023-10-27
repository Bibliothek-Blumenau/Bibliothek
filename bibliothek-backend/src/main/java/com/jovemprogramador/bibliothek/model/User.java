package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "TB_User")
public class User implements UserDetails {

    @Id
    private String matricula;
    @NotEmpty
    private String nomeCompleto;
    @NotEmpty
    private String password;
    @NotEmpty
    private String roles;
    private String fotoPerfil;

    public User(String matricula, String nomeCompleto, String password, String roles, String fotoPerfil) {
        this.matricula = matricula;
        this.nomeCompleto = nomeCompleto;
        this.password = password;
        this.roles = roles;
        this.fotoPerfil = fotoPerfil;
    }

    public User() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return matricula;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public @NotEmpty String getNomeCompleto() {
        return this.nomeCompleto;
    }

    public @NotEmpty String getRoles() {
        return this.roles;
    }

    public String getFotoPerfil() {
        return this.fotoPerfil;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNomeCompleto(@NotEmpty String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public void setRoles(@NotEmpty String roles) {
        this.roles = roles;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}