package com.jovemprogramador.bibliothek.repository;

import com.jovemprogramador.bibliothek.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByMatricula(String matricula);
}
