package com.tim20.rivera.controller;

import com.tim20.rivera.model.Boat;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boat")
public class BoatController {

    @Autowired
    private BoatService boatService;

    @GetMapping(path = "/get-boats")
    public List<Boat> getBoats() {
        return boatService.findAll();
    }

}