package com.gescptbank.web;

import com.gescptbank.dto.CompteDto;
import com.gescptbank.services.CompteService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping(value = "/api/v1")
@CrossOrigin("*")
public class ComptBancaireRestController {

    //Injection de servives;
    private final CompteService compteService;
    ComptBancaireRestController(CompteService compteService) {
        this.compteService = compteService;
    }

    @PostMapping("/comptes")
    CompteDto createCompte(@RequestBody CompteDto dto){
        this.compteService.createCompte(dto);;
        return  dto;
    }

    @GetMapping("/comptes/type/{type}")
    List<?> findAllCompte(@PathVariable("type") String type){
        if(type.equals("CC"))
            return this.compteService.findAllCompteCourant();
        if(type.equals("CE"))
            return this.compteService.findAllCompteEpargne();
        return null;
    }


    @GetMapping("/comptes/courant/{numCompte}")
    ResponseEntity<?> findCompteCourant(@PathVariable("numCompte") String numCompte) {
        return ResponseEntity.ok(this.compteService.findCompteCourant(numCompte));
    }

    @GetMapping("/comptes/epargne/{numCompte}")
    ResponseEntity<?> findCompteEpargne(@PathVariable("numCompte") String numCompte) {
        return ResponseEntity.ok(this.compteService.findCompteEpargne(numCompte));
    }

    @GetMapping("/comptes/active/{numCompte}")
    boolean activeCompte(@PathVariable("numCompte") String numCompte) {
        this.compteService.activeCompte(numCompte);
        return true;
    }

    @GetMapping("/comptes/suspendre/{numCompte}")
    boolean suspendreCompte(@PathVariable("numCompte") String numCompte) {
        this.compteService.suspendCompte(numCompte);
        return true;
    }

    @GetMapping("/comptes/{numCompte}/{type}")
    ResponseEntity<?> findCompte(@PathVariable("numCompte") String numCompte, @PathVariable("type") String type) {
        if(type.equals("CC"))
            return ResponseEntity.ok(findCompteCourant(numCompte));
        if (type.equals("CE"))
            return ResponseEntity.ok(findCompteEpargne(numCompte));
        return null;

    }

}
