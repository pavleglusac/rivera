package com.tim20.rivera.service;

import com.tim20.rivera.model.Boat;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.repository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {
    @Autowired
    BoatRepository boatRepository;

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/boats/";

    public List<Boat> findAll() {
        return boatRepository.findAll();
    }
}
