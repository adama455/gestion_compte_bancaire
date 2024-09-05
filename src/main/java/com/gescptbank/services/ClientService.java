package com.gescptbank.services;

import com.gescptbank.dto.ClientDto;
import com.gescptbank.entities.Client;

import java.util.List;

public interface ClientService {

    Client createClient(ClientDto dto);
    List<Client> getAllClient();
    Client getClientById(long id);
    List<Client> searchClientsByKeywords(String keyword);
}
