package com.tim20.rivera.controller;

import com.tim20.rivera.dto.ClientDTO;
import com.tim20.rivera.dto.ClientRequestDTO;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(path = "/get-clients")
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @PostMapping(path = "add-client")
    public ResponseEntity<String> addClient(ClientDTO clientDTO) {
        clientService.addClient(clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-client-by-username")
    public ClientRequestDTO getClientByUsername(@RequestParam("username") String username) {
        return clientService.getClientByUsername(username);
    }

    @GetMapping(path = "get-client")
    public ClientDTO getClient(@RequestParam("id") String id) {
        return clientService.getClient(id);
    }

    @PostMapping(path = "update-client")
    public ResponseEntity<String> updateClient(
            ClientDTO clientDTO,
            @RequestPart(value = "photo", required = false) MultipartFile multipartFile
    ) throws IOException {
        clientService.updateClient(clientDTO, multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "activate-client")
    public Client activateClient(@RequestParam("username") String username) {
        System.out.println("activate");
        return clientService.activateClient(username);
    }
}
