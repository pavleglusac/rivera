package com.tim20.rivera.controller;

import com.tim20.rivera.model.Client;
import com.tim20.rivera.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(path = "/get-clients")
    public List<Client> getClients() {
        return clientService.getClients();
    }
}
