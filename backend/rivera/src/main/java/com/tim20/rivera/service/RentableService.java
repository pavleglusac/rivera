package com.tim20.rivera.service;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.RentableDTO;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.OwnerRepository;
import com.tim20.rivera.repository.RentableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RentableService {

    @Autowired
    private RentableRepository rentableRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public List<RentableDTO> getRentablesDtoForOwner(String ownerId) {
        Owner owner = ownerRepository.findByUsername(ownerId);
        if(owner == null) return null;
        return owner.getRentables().stream().map(this::rentableToDto).collect(Collectors.toList());
    }

    public List<Rentable> getRentablesForOwner(String ownerId) {
        Owner owner = ownerRepository.findByUsername(ownerId);
        if(owner == null) return null;
        return owner.getRentables();
    }

    public RentableDTO rentableToDto(Rentable rentable) {
        RentableDTO dto = new RentableDTO();
        dto.setAddress(rentable.getAddress());
        dto.setId(rentable.getId());
        dto.setAverageScore(rentable.getAverageScore());
        dto.setDescription(rentable.getDescription());
        Pricelist pricelist = rentable.getCurrentPricelist();
        dto.setCancellationTerms(pricelist.getCancellationTerms());
        dto.setName(rentable.getName());
        dto.setTags(rentable
                .getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
        );
        dto.setCity(rentable.getCity());
        dto.setCountry(rentable.getCountry());
        dto.setPerDay(pricelist.getPricePerDay());
        dto.setPerHour(pricelist.getPricePerHour());
        dto.setServices(rentable.getAdditionalServices());
        dto.setPictures(rentable.getPictures());
        dto.setRulesOfConduct(rentable.getRulesOfConduct());
        dto.setId(rentable.getId());
        return dto;
    }

}
