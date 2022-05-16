package com.tim20.rivera.controller;

import com.tim20.rivera.dto.RentableDTO;
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

    @GetMapping(path = "rentables-for-owner")
    public List<RentableDTO> getRentableByOwner(String ownerId) {
        return rentableService.getRentablesDtoForOwner(ownerId);
    }
}
