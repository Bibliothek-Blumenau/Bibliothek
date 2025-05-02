package com.jovemprogramador.bibliothek.domain.loan;

import com.jovemprogramador.bibliothek.domain.user.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanEntity, UUID> {
    List<LoanEntity> findByUser(UserEntity user, Sort sort);
}
