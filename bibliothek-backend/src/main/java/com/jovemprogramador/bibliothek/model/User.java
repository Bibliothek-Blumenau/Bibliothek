package com.jovemprogramador.bibliothek.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_User")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    private String matricula;
    @NotEmpty
    private String nomeCompleto;
    @NotEmpty
    private String password;
    @NotEmpty
    private String roles;

    @OneToMany(mappedBy = "user")
    private List<Emprestimo> emprestimos;

    public User(String matricula, String nomeCompleto, String password, String roles) {
        this.matricula = matricula;
        this.nomeCompleto = nomeCompleto;
        this.password = password;
        this.roles = roles;
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
}