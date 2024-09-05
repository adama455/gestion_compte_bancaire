package com.gescptbank.web;

import com.gescptbank.dto.ClientDto;
import com.gescptbank.entities.Client;
import com.gescptbank.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin("*")
public class ClientRestController {

    private final ClientService clientService;

    ClientRestController(final ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    void createClient(@RequestBody ClientDto dto){
        this.clientService.createClient(dto);
    }

    @GetMapping("/clients")
    List<Client> findAll(){
        return this.clientService.getAllClient();
    }

    @GetMapping("/clients/{id}")
    Client findOne(@PathVariable("id") long id){
        return this.clientService.getClientById(id);
    }
}
