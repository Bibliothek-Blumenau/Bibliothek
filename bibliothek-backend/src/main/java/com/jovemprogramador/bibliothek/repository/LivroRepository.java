package com.jovemprogramador.bibliothek.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jovemprogramador.bibliothek.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
	Livro findById(long codLivro);
	@Query("SELECT l FROM Livro l WHERE l.destaque = TRUE")
    List<Livro> findByDestaque();
	@Query("SELECT l FROM Livro l WHERE l.estadoLivro = FALSE")
    Page<Livro> findByEstadoLivro(Pageable pageable);
	@Query("SELECT l FROM Livro l WHERE l.titulo LIKE :titulo AND l.estadoLivro = FALSE")
    List<Livro> findByTitulo(@Param("titulo") String titulo);
	@Query("SELECT l FROM Livro l WHERE l.autor LIKE :autor AND l.estadoLivro = FALSE")
    List<Livro> findByAutor(@Param("autor") String autor);
	@Query("SELECT l FROM Livro l WHERE l.genero LIKE :genero AND l.estadoLivro = FALSE")
    List<Livro> findByGenero(@Param("genero") String genero);
}
