package com.gescptbank.services;

import com.gescptbank.dto.OperationDto;
import com.gescptbank.entities.CompteBancaire;
import com.gescptbank.entities.Operation;

import java.util.List;

public interface OperationService {
    CompteBancaire effectuerVersement(String numCompte, double montant);
    CompteBancaire effectuerRetrait(String numCompte, double montant);
    List<Operation> findByClientNumCompte(String  numCompte);
    CompteBancaire virementFromOneCompteAToCompteB(String numCompteSource, String numCompteDestination, double montant);
}
