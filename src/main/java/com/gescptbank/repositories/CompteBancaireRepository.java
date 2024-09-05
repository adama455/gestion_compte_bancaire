package com.gescptbank.repositories;

import com.gescptbank.entities.CompteBancaire;
import com.gescptbank.entities.CompteEpargne;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {
    Optional<CompteBancaire> findByNumCompte(@Param("numCompte") String numCompte);
}
