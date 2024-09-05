package com.gescptbank.web;

import com.gescptbank.entities.Operation;
import com.gescptbank.dto.OperationDto;
import com.gescptbank.services.OperationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin("*")
public class OperationRestController {

    private final OperationService operationService;
    OperationRestController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("operations/versements")
    public void versements(@RequestBody OperationDto dto) {
        operationService.effectuerVersement(dto.getNumCompte(), dto.getAmount());
    }

    @PostMapping("operations/retraits")
    public void retraits(@RequestBody OperationDto dto) {
        operationService.effectuerRetrait(dto.getNumCompte(), dto.getAmount());
    }

    @PostMapping("operations/virements")
    public void virements(@RequestBody OperationDto dto) {
        operationService.virementFromOneCompteAToCompteB(dto.getNumCompteSource(), dto.getNumCompteDestination(), dto.getAmount());
    }

    @GetMapping("/operations/client/{numCompte}")
    List<Operation> findAllOperationByClient(@PathVariable("numCompte") String numCompte) {
        return this.operationService.findByClientNumCompte(numCompte);
    }

}
