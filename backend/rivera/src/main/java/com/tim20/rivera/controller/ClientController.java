package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.service.ClientService;
import com.tim20.rivera.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;

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
    public ResponseEntity<String> updateClient(ClientDTO clientDTO) {
        clientService.updateClient(clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "update-client-photo")
    public ResponseEntity<String> updateClientPhoto(@RequestParam("username") String username, @RequestPart(value = "image") MultipartFile image) throws IOException {
        clientService.updateClientPhoto(username, image);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "activate-client")
    public Client activateClient(@RequestParam("username") String username) {
        System.out.println("activate");
        return clientService.activateClient(username);
    }

    @GetMapping(path = "get-subscribed-entities")
    public List<EntityDTO> getSubscribedEntities(@RequestParam("username") String username, String search) {
        return clientService.getSubscribedEntities(username, search);
    }

    @PostMapping(path = "is-subscribed")
    public Boolean isSubscribed(@RequestParam("username") String username, Integer id) {
        return clientService.isSubscribed(username, id);
    }

    @PostMapping(path = "subscribe")
    public ResponseEntity<String> subscribe(@RequestParam("username") String username, Integer id) {
        clientService.subscribe(username, id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam("username") String username, Integer id) {
        clientService.unsubscribe(username, id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-reservations")
    public List<ClientReservationDTO> getReservations(@RequestParam("username") String username, ReservationSearch search) {
        return reservationService.getReservations(username, search);
    }
}
