package com.jovemprogramador.bibliothek.repository;

import com.jovemprogramador.bibliothek.model.Emprestimo;
import com.jovemprogramador.bibliothek.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuario(User usuario, Sort sort);
}
