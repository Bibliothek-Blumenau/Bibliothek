package com.jovemprogramador.bibliothek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jovemprogramador.bibliothek.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByMatricula(String matricula);
    
    List<User> findByResetPassword(boolean resetPassword);
    
    //JPQL
    @Query("SELECT u FROM User u WHERE LOWER(u.matricula) = LOWER(:matricula) AND LOWER(u.nomeCompleto) = LOWER(:nomeCompleto)")
    User findByMatriculaAndNomeCompletoIgnoreCase(@Param("matricula") String matricula, @Param("nomeCompleto") String nomeCompleto);
}