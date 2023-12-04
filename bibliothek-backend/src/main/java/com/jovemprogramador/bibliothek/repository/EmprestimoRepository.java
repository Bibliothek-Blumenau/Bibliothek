package com.jovemprogramador.bibliothek.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jovemprogramador.bibliothek.model.Emprestimo;
import com.jovemprogramador.bibliothek.model.User;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuario(User usuario, Sort sort);

    //JPQL
    @Query("SELECT e.livro.codLivro, COUNT(e) FROM Emprestimo e GROUP BY e.livro.codLivro ORDER BY COUNT(e) DESC")
    List<Object[]> findMostBorrowedBooks();

    @Query("SELECT e.livro.codLivro FROM Emprestimo e GROUP BY e.livro.codLivro ORDER BY COUNT(e) DESC")
    List<Long> findMostBorrowedBookCodes();
    
}