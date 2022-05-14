package com.tim20.rivera.service;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.RentableDTO;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.Tag;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RentableService {

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
