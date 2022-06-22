package com.tim20.rivera.services;

import com.tim20.rivera.dto.BoatDTO;
import com.tim20.rivera.model.BoatType;
import com.tim20.rivera.repository.BoatRepository;
import com.tim20.rivera.service.BoatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BoatServiceTest {

    @Autowired
    private BoatService boatService;

    @Autowired
    private BoatRepository boatRepository;

    @Test
    public void testInsert(){
        int numberOfBoats = boatRepository.findAll().size();
        List<String> tags = new ArrayList<>();
        List<String> services = new ArrayList<>();
        List<String> rules = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");
        services.add("service1");
        services.add("service2");
        services.add("service3");
        rules.add("rules1");
        rules.add("rules2");
        rules.add("rules3");
        BoatDTO boatDTO = new BoatDTO(8,"ime","opis",tags,services,rules,10d,100d,4.5d,"adresa","grad","drzava","10",null, BoatType.CRUISE,500d,50,7,7d,7d);
        try {
            boatService.insert(boatDTO,null);
        } catch (IOException e) {
            assert false;
        }
        assert boatRepository.findAll().size() == numberOfBoats + 1;
    }

    @Test
    @Transactional
    public void testUpdate(){
        BoatDTO boatDTO = boatService.getById(8);
        boatDTO.setName("Novo ime");
        boatDTO.setCapacity(123456);
        try {
            boatService.update(boatDTO,null);
        } catch (IOException e) {
            assert false;
        }

        assert boatService.getById(8).getName().equals("Novo ime") && boatService.getById(8).getCapacity() == 123456;
    }
}
