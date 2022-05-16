package com.tim20.rivera.service;

import com.tim20.rivera.dto.DiscountOfferDTO;
import com.tim20.rivera.dto.DiscountProfileDTO;
import com.tim20.rivera.model.Discount;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Tag;
import com.tim20.rivera.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final int NUMBER_OF_OFFERS_SHOWING = 4;
    @Autowired
    private DiscountRepository discountRepository;

    public DiscountProfileDTO discountTODPDto(Discount discount) {
        DiscountProfileDTO discountProfileDTO = new DiscountProfileDTO();
        discountProfileDTO.setCapacity(discount.getCapacity());
        discountProfileDTO.setId(discount.getId());
        discountProfileDTO.setEnd(discount.getEndDateTime());
        discountProfileDTO.setStart(discount.getStartDateTime());
        discountProfileDTO.setTags(discount.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        discountProfileDTO.setCapacity(discount.getCapacity());
        discountProfileDTO.setPrice(discount.getPrice());
        return discountProfileDTO;
    }

    public DiscountOfferDTO discountToDODTO(Discount discount) {
        DiscountOfferDTO discountOfferDTO = new DiscountOfferDTO();
        discountOfferDTO.setCapacity(discount.getCapacity());
        discountOfferDTO.setId(discount.getId());
        discountOfferDTO.setName(discount.getRentable().getName());
        discountOfferDTO.setEnd(discount.getEndDateTime());
        discountOfferDTO.setStart(discount.getStartDateTime());
        discountOfferDTO.setPrice(discount.getPrice());
        discountOfferDTO.setCity(discount.getRentable().getCity());
        discountOfferDTO.setCountry(discount.getRentable().getCountry());
        discountOfferDTO.setAverageScore(discount.getRentable().getAverageScore());
        discountOfferDTO.setPicture(discount.getRentable().getPictures().get(0));
        return discountOfferDTO;
    }

    public List<DiscountOfferDTO> getRecommendedOffers() {
        ArrayList<DiscountOfferDTO> offers = new ArrayList<>();
        List<Discount> list = discountRepository.findAll();
        Collections.shuffle(list);
        for (Discount d : list) {
            offers.add(discountToDODTO(d));
        }
        return offers.stream().limit(NUMBER_OF_OFFERS_SHOWING).collect(Collectors.toList());
    }

    public List<DiscountOfferDTO> getLastMinuteOffers() {
        ArrayList<DiscountOfferDTO> offers = new ArrayList<>();
        for (Discount d : discountRepository.findAll().stream().sorted(Comparator.comparing(Discount::getStartDateTime)).toList()) {
            offers.add(discountToDODTO(d));
        }
        return offers.stream().limit(NUMBER_OF_OFFERS_SHOWING).collect(Collectors.toList());
    }

    public List<DiscountOfferDTO> getBestPriceOffers() {
        ArrayList<DiscountOfferDTO> offers = new ArrayList<>();
        for (Discount d : discountRepository.findAll().stream().sorted(Comparator.comparing(Discount::getPrice)).toList()) {
            offers.add(discountToDODTO(d));
        }
        return offers.stream().limit(NUMBER_OF_OFFERS_SHOWING).collect(Collectors.toList());
    }

    public List<DiscountOfferDTO> getMostPopularOffers() {
        ArrayList<DiscountOfferDTO> offers = new ArrayList<>();
        for (Discount d : discountRepository.findAll().parallelStream()
                                            .sorted(Comparator.comparingDouble(p -> p.getRentable().getAverageScore())).toList()) {
            offers.add(discountToDODTO(d));
        }
        return offers.stream().limit(NUMBER_OF_OFFERS_SHOWING).collect(Collectors.toList());
    }

    public List<DiscountOfferDTO> getDiscountsForOwner(Owner owner) {
        List<Discount> discounts = new ArrayList<>();
        owner.getRentables()
             .forEach(rentable -> discounts.addAll(rentable.getDiscounts()));
        return discounts.stream().map(this::discountToDODTO).collect(Collectors.toList());
    }

}
