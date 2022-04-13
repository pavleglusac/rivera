package com.tim20.rivera.controller;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.model.Calendar;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Discount;
import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/cottage")
public class CottageController {

    @Autowired
    private CottageService cottageService;

    @PostMapping(path="/add-cottage")
    public ResponseEntity<String> addCottage(
            CottageDTO cottage,
            @RequestPart("images") MultipartFile[] multipartFiles
    ) throws IOException {

        cottageService.insert(cottage, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
    @GetMapping(path = "/get-cottages")
    public List<Cottage> getCottages() {
        return cottageService.findAll();
    }

    @GetMapping(path="get-cottage")
    public CottageDTO getCottage(@RequestParam("id") Integer id) throws IOException {
        return cottageService.getById(id);
    }


    @PostMapping(path="update-cottage")
    public ResponseEntity<String> updateCottage(
            CottageDTO cottage,
            @RequestPart(value = "images", required = false) MultipartFile[] multipartFiles
    ) throws IOException {
        cottageService.update(cottage, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path="delete-cottage")
    public ResponseEntity<String> deleteCottage(@RequestParam Integer id){
        cottageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }



}