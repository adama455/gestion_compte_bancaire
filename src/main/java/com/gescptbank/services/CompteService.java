package com.gescptbank.services;
import com.gescptbank.dto.CompteDto;
import com.gescptbank.entities.CompteBancaire;
import com.gescptbank.entities.CompteCourant;
import com.gescptbank.entities.CompteEpargne;
import java.util.List;

public interface CompteService {

    void createCompte(CompteDto dto);
    List<CompteCourant> findAllCompteCourant();
    List<CompteEpargne> findAllCompteEpargne();
    CompteEpargne findCompteEpargne(String numCompte);
    CompteCourant findCompteCourant(String numCompte);
    CompteBancaire suspendCompte(String numCompte);
    CompteBancaire activeCompte(String numCompte);
    List<CompteBancaire> findAllCompteClientById(long id);
    List<CompteBancaire> findAllComptesCreatedByEmployeeById(long id);

}
