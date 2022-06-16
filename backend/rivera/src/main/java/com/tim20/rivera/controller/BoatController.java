package com.tim20.rivera.controller;

import com.tim20.rivera.dto.AdventureDTO;
import com.tim20.rivera.dto.BoatDTO;
import com.tim20.rivera.dto.BoatProfileDTO;
import com.tim20.rivera.dto.SearchParams;
import com.tim20.rivera.model.Boat;
import com.tim20.rivera.model.Boat;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/boat")
public class BoatController {

    @Autowired
    private BoatService boatService;



    @PostMapping(path = "/add-boat")
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseEntity<Integer> addBoat(
            BoatDTO boat,
            @RequestPart("images") MultipartFile[] multipartFiles
    ) throws IOException {

        Integer id = boatService.insert(boat, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping(path = "/get-boats")
    public List<Boat> getBoats() {
        return boatService.findAll();
    }

    @GetMapping(path = "get-boat")
    public BoatDTO getBoat(@RequestParam("id") Integer id) {
        return boatService.getById(id);
    }

    @GetMapping(path = "get-full-boat")
    public BoatProfileDTO getFullBoat(@RequestParam("id") Integer id) throws IOException {
        return boatService.getFullById(id);
    }

    @PostMapping(path = "update-boat")
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseEntity<String> updateBoat(
            BoatDTO boat,
            @RequestPart(value = "images", required = false) MultipartFile[] multipartFiles
    ) throws IOException {
        boatService.update(boat, multipartFiles);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "delete-boat")
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseEntity<String> deleteBoat(@RequestParam Integer id) {
        boatService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }




    @PostMapping(path="/search-boats")
    public List<BoatDTO> searchBoats(SearchParams searchParams) {
        return boatService.searchBoats(searchParams);
    }

}