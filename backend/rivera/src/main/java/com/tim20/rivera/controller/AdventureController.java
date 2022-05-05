package com.tim20.rivera.controller;

import com.tim20.rivera.dto.AdventureDTO;
import com.tim20.rivera.dto.AdventureProfileDTO;
import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdventureController {
    @Autowired
    private AdventureService adventureService;

    @PostMapping(path = "add-adventure")
    public ResponseEntity<String> addAdventure(
            AdventureDTO adventure,
            @RequestPart("images") MultipartFile[] multipartFiles
    ) throws IOException {

        adventureService.addAdventure(adventure, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-adventure")
    public AdventureDTO getAdventure(@RequestParam("id") Integer id) {
        return adventureService.getAdventure(id);
    }

    @PostMapping(path="/search-adventures")
    public List<AdventureDTO> searchAdventures(SearchParams searchParams) {
        return adventureService.searchAdventures(searchParams);
    }

    @GetMapping(path = "/get-adventures")
    public List<AdventureDTO> getAdventures() {
        return adventureService.getAdventures();
    }

    @PostMapping(path = "update-adventure")
    public ResponseEntity<String> updateAdventure(
            AdventureDTO adventure,
            @RequestPart(value = "images", required = false) MultipartFile[] multipartFiles
    ) throws IOException {

        adventureService.updateAdventure(adventure, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-full-adventure")
    public AdventureProfileDTO getFullAdventure(@RequestParam("id") Integer id) throws IOException {
        return adventureService.getFullById(id);
    }

    @PostMapping(path = "delete-adventure")
    public ResponseEntity<String> deleteAdventure(@RequestParam Integer id) {
        adventureService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

}
