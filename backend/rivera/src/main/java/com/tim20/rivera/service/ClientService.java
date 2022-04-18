package com.tim20.rivera.service;

import com.tim20.rivera.dto.ClientRentableDto;
import com.tim20.rivera.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    public ClientRentableDto clientToCRDto(Client client){
        ClientRentableDto clientRentableDto = new ClientRentableDto();
        clientRentableDto.setUsername(client.getUsername());
        clientRentableDto.setName(client.getName());
        clientRentableDto.setEmail(client.getEmail());
        clientRentableDto.setPhoto(client.getPhoto());
        clientRentableDto.setSurname(client.getSurname());
        return clientRentableDto;
    }
}
