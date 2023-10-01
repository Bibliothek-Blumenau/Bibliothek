package com.jovemprogramador.bibliothek.repository;

import com.jovemprogramador.bibliothek.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByMatricula(String matricula);
}
