package dev.williamnogueira.bibliothek.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByRegistration(String registration);
    boolean existsByRegistration(String registration);
}
