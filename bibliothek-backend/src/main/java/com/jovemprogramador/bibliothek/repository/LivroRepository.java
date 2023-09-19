package com.jovemprogramador.bibliothek.repository;

import com.jovemprogramador.bibliothek.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

}
