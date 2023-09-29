package com.jovemprogramador.bibliothek.repository;

import com.jovemprogramador.bibliothek.model.Livro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
    List<Livro> findByDestaqueIsTrue();
    Livro findById(long cod_livro);
    List<Livro> findAllByTituloLike(String titulo);
    List<Livro> findAllByAutorLike(String titulo);
    List<Livro> findAllByGeneroLike(String titulo);
}
