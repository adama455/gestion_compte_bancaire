package com.gescptbank.services;

import com.gescptbank.dto.CompteDto;
import com.gescptbank.entities.Client;
import com.gescptbank.entities.CompteBancaire;
import com.gescptbank.entities.CompteCourant;
import com.gescptbank.entities.CompteEpargne;
import com.gescptbank.enums.AccountStatus;
import com.gescptbank.repositories.CompteBancaireRepository;
import com.gescptbank.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompteServiceImpl implements CompteService {

    private final CompteBancaireRepository bancaireRepository;
    private final PersonRepository personRepository;

    private final EmailService emailService;
    CompteServiceImpl(
            final CompteBancaireRepository bancaireRepository,
            final PersonRepository personRepository,
            final EmailService emailService
    ) {
        this.bancaireRepository = bancaireRepository;
        this.personRepository = personRepository;
        this.emailService =emailService;
    }

    @Override
    public void createCompte(CompteDto dto) {
        Client client = null;
        boolean isPresent = this.personRepository.findById(dto.getClientId()).isPresent();

        //CREATION COMPTE EPARGNE
        if(isPresent && dto.getDecouvert() == 0 && dto.getTauxInteret() > 0 ) {
            client = (Client) this.personRepository.getReferenceById(dto.getClientId());
            System.out.println("############résultat#######" + client);
            CompteEpargne compteEpargne = new  CompteEpargne();
            compteEpargne.setClient(client);
            compteEpargne.setCreatedAt(new Date());
            compteEpargne.setDevis("CFA");
            compteEpargne.setBalance(dto.getBalance());
            compteEpargne.setTauxInteret(dto.getTauxInteret());
            compteEpargne.setStatus(AccountStatus.ACTIVATED);
            compteEpargne.setNumCompte(generateAccountNumber(5));
            this.bancaireRepository.save(compteEpargne);
            emailService.sendNotificationEmail(client.getEmail(),
                    "Notification, Création de Compte,",
                    "Bonjour cher client: "
                            + client.getFirstName() + " "
                            + client.getLastName()  + " " +
                            "Vous avez créé un compte, pour accéder à votre espace et pour suivre" +
                            " Votre inscription voici vos crendentials de connexion:" +
                            "LOGIN: " +client.getEmail() +
                            " Mot de passe: "+generateAccountNumber(6)
            );
        }

        //CREATION COMPTE COURANT
        if(isPresent && dto.getTauxInteret()  == 0 && dto.getDecouvert() > 0)
        {
            client = (Client) this.personRepository.getReferenceById(dto.getClientId());
            CompteCourant compteCourant = new CompteCourant();
            compteCourant.setClient(client);
            compteCourant.setBalance(dto.getBalance());
            compteCourant.setDevis("CFA");
            compteCourant.setCreatedAt(new Date());
            compteCourant.setDecouvert(dto.getDecouvert());
            compteCourant.setStatus(AccountStatus.ACTIVATED);
            compteCourant.setNumCompte(generateAccountNumber(5));
            this.bancaireRepository.save(compteCourant);

            emailService.sendNotificationEmail(client.getEmail(),
                    "Notification, Création de Compte,",
                    "Bonjour cher client: "
                            + client.getFirstName() + " "
                            + client.getLastName()  + " " +
                            "Vous avez créé un compte, pour accéder à votre espace et pour suivre" +
                            " Votre inscription voici vos crendentials de connexion:" +
                            "LOGIN: " +client.getEmail() +
                            " Mot de passe: "+generateAccountNumber(6)
            );
        }
    }

    @Override
    public List<CompteCourant> findAllCompteCourant() {
        List<CompteCourant> l = new ArrayList<>();
        for(CompteBancaire c: this.bancaireRepository.findAll())
        {
            if(c instanceof CompteCourant)
                l.add((CompteCourant)c);
        }
        return l;
    }

    @Override
    public List<CompteEpargne> findAllCompteEpargne() {
        List<CompteEpargne> l = new ArrayList<>();
        for(CompteBancaire c: this.bancaireRepository.findAll())
        {
            if(c instanceof  CompteEpargne)
                l.add((CompteEpargne)c);
        }
        return l;
    }

    @Override
    public CompteEpargne findCompteEpargne(String numCompte) {
        try {
            return (CompteEpargne)this.bancaireRepository.findByNumCompte(numCompte).get();
        }catch (Exception e){
            throw new RuntimeException("Ce compte n'existe pas !");
        }

    }

    @Override
    public CompteCourant findCompteCourant(String numCompte) {
        try{
            return (CompteCourant)this.bancaireRepository.findByNumCompte(numCompte).get();
        }catch (Exception e){
            throw new RuntimeException("Ce compte n'existe pas !");
        }
    }

    @Override
    public CompteBancaire suspendCompte(String numCompte) {
        Optional<CompteBancaire>  compte = this.bancaireRepository.findByNumCompte(numCompte);
        if(compte.isPresent() && compte.get().getStatus().equals(AccountStatus.ACTIVATED)){
            CompteBancaire c = compte.get();
            c.setStatus(AccountStatus.SUSPENDED);
            return this.bancaireRepository.save(c);
        }
        return null;
    }

    @Override
    public CompteBancaire activeCompte(String numCompte) {
        Optional<CompteBancaire>  compte = this.bancaireRepository.findByNumCompte(numCompte);
        if(compte.isPresent() && compte.get().getStatus().equals(AccountStatus.SUSPENDED)){
            CompteBancaire c = compte.get();
            c.setStatus(AccountStatus.ACTIVATED);
            return this.bancaireRepository.save(c);
        }
        return null;
    }

    @Override
    public List<CompteBancaire> findAllCompteClientById(long id) {
        return List.of();
    }

    @Override
    public List<CompteBancaire> findAllComptesCreatedByEmployeeById(long id) {
        return List.of();
    }


    private static String generateAccountNumber(int n) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // Les 4 premiers chiffres sont 0
        for (int i = 0; i < 4; i++) {
            sb.append("0");
        }
        // Les 4 chiffres suivants sont 0 ou 1
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(2));
        }
        // Les 5 derniers chiffres sont générés aléatoirement
        for (int i = 0; i < n; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
