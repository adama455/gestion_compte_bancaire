package com.gescptbank.services;

import com.gescptbank.dto.OperationDto;
import com.gescptbank.entities.CompteBancaire;
import com.gescptbank.entities.Operation;
import com.gescptbank.enums.AccountStatus;
import com.gescptbank.enums.TypeOperation;
import com.gescptbank.repositories.CompteBancaireRepository;
import com.gescptbank.repositories.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OperationServiceImpl implements OperationService {

    private OperationRepository operationRepository;
    private CompteBancaireRepository compteBancaireRepository;

    public OperationServiceImpl(OperationRepository operationRepository, CompteBancaireRepository compteBancaireRepository) {
        this.operationRepository = operationRepository;
        this.compteBancaireRepository = compteBancaireRepository;
    }

    @Override
    public CompteBancaire effectuerVersement(String numCompte, double montant) {
        Optional<CompteBancaire> compteOpt = compteBancaireRepository.findByNumCompte(numCompte);
        if (compteOpt.isPresent()) {
            CompteBancaire compte = compteOpt.get();
            if (compte.getStatus().equals(AccountStatus.ACTIVATED)) {
                compte.setBalance(compte.getBalance() + montant);
                Operation operation = new Operation();
                operation.setDateOperation(new Date());
                operation.setAmount(montant);
                operation.setCompte(compte);
                operation.setTypeOperation(TypeOperation.CREDIT);
                operation.setNumOperation(generateNumOp());
                this.operationRepository.save(operation);

                return compteBancaireRepository.save(compte);
            }else {
                throw   new RuntimeException("Le Compté est bloqué, Opération Impossible");
            }
        }
        return null;
    }

    @Override
    public CompteBancaire effectuerRetrait(String numCompte, double montant) {
        Optional<CompteBancaire> compteOpt = compteBancaireRepository.findByNumCompte(numCompte);
        if (compteOpt.isPresent()) {
            CompteBancaire compte = compteOpt.get();
            if (compte.getStatus().equals(AccountStatus.ACTIVATED)) {
                if (compte.getBalance() > montant) {
                    compte.setBalance(compte.getBalance() - montant);
                    compte = compteBancaireRepository.save(compte);
                    Operation operation = new Operation();
                    operation.setDateOperation(new Date());
                    operation.setAmount(montant);
                    operation.setCompte(compte);
                    operation.setTypeOperation(TypeOperation.DEBIT);
                    operation.setNumOperation(generateNumOp());
                    this.operationRepository.save(operation);
                } else {
                    throw new RuntimeException("Solde insuffisant");
                }
            }else {
                throw new RuntimeException("Le compte est bloqué, Opération Impossible");
            }
            return  compte;
        }
        return null;
    }

    @Override
    public List<Operation> findByClientNumCompte(String numCompte) {
        List<Operation> list =  new ArrayList<>();
        for(Operation o:this.operationRepository.findAll()){
            if(o.getCompte().getNumCompte().equals(numCompte)){
                list.add(o);
            }
        }
        return list;
    }

    @Override
    public CompteBancaire virementFromOneCompteAToCompteB(String numCompteSource,String numCompteDestination, double montant) {
        CompteBancaire source = effectuerRetrait(numCompteSource, montant);
        if (source != null) {
            return effectuerVersement(numCompteDestination, montant);
        }
        return  null;
    }

    private static String generateNumOp() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            sb.append("0");
        }
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(2));
        }
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
