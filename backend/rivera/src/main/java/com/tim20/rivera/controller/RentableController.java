package com.tim20.rivera.controller;

import com.tim20.rivera.dto.RentableDTO;
import com.tim20.rivera.service.AdventureService;
import com.tim20.rivera.service.BoatService;
import com.tim20.rivera.service.CottageService;
import com.tim20.rivera.service.RentableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentableController {

    @Autowired
    private RentableService rentableService;
    @Autowired
    private CottageService cottageService;
    @Autowired
    private BoatService boatService;
    @Autowired
    private AdventureService adventureService;

    @GetMapping(path = "rentables-for-owner")
    public List<RentableDTO> getRentableByOwner(String ownerId) {
        return rentableService.getRentablesDtoForOwner(ownerId);
    }

    @GetMapping(path = "get-type-of-rentable")
    public String getTypeOfRentable(Integer id) {
        if(cottageService.getById(id) != null){
            return "cottage";
        }
        if(boatService.getById(id) != null){
            return "boat";
        }
        if(adventureService.getById(id) != null){
            return "adventure";
        }
        return "";
    }
}
