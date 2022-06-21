package com.tim20.rivera.service;

import com.tim20.rivera.dto.DiscountDTO;
import com.tim20.rivera.dto.DiscountOfferDTO;
import com.tim20.rivera.dto.DiscountProfileDTO;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final int NUMBER_OF_OFFERS_SHOWING = 4;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private RentableRepository rentableRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private CottageRepository cottageRepository;
    @Autowired
    private BoatRepository boatRepository;
    @Autowired
    private AdventureRepository adventureRepository;

    public DiscountProfileDTO discountTODPDto(Discount discount) {
        DiscountProfileDTO discountProfileDTO = new DiscountProfileDTO();
        discountProfileDTO.setCapacity(discount.getCapacity());
        discountProfileDTO.setId(discount.getId());
        discountProfileDTO.setEnd(discount.getEndDateTime());
        discountProfileDTO.setStart(discount.getStartDateTime());
        discountProfileDTO.setTags(discount.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        discountProfileDTO.setAdditionalServices(discount.getAdditionalServices());
        discountProfileDTO.setCapacity(discount.getCapacity());
        discountProfileDTO.setPrice(discount.getPrice());
        discountProfileDTO.setReserved(discount.isReserved());
        return discountProfileDTO;
    }

    public DiscountOfferDTO discountToDODTO(Discount discount) {
        DiscountOfferDTO discountOfferDTO = new DiscountOfferDTO();
        discountOfferDTO.setCapacity(discount.getCapacity());
        discountOfferDTO.setId(discount.getId());
        discountOfferDTO.setRentableId(discount.getRentable().getId());
        discountOfferDTO.setName(discount.getRentable().getName());
        discountOfferDTO.setEnd(discount.getEndDateTime());
        discountOfferDTO.setStart(discount.getStartDateTime());
        discountOfferDTO.setPrice(discount.getPrice());
        discountOfferDTO.setCity(discount.getRentable().getCity());
        discountOfferDTO.setCountry(discount.getRentable().getCountry());
        discountOfferDTO.setAverageScore(discount.getRentable().getAverageScore());
        discountOfferDTO.setPicture(discount.getRentable().getPictures().get(0));
        discountOfferDTO.setReserved(discount.isReserved());
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

    @Transactional
    public boolean addDiscount(DiscountDTO discountDTO) {
        try {
            int rentableId = discountDTO.getRentableId();
            Rentable rentable = rentableRepository.findById(rentableId).get();
            if (rentable instanceof Cottage) {
                rentable = cottageRepository.findOneById(rentableId);
            } else if (rentable instanceof Boat) {
                rentable = boatRepository.findOneById(rentableId);
            } else {
                rentable = adventureRepository.findOneById(rentableId);
            }
            discountRepository.save(discountDTOToDiscount(discountDTO, rentable));
            sendNotificationsToSubscribed(rentable, discountDTO);
            return true;
        } catch (PessimisticLockingFailureException exception) {
            return false;
        }

    }

    private void sendNotificationsToSubscribed(Rentable rentable, DiscountDTO discountDTO) {
        for(String username : rentable.getSubscribed()) {
            try {
                emailService.sendNotificaitionToUsername(username, "New Discount!",
                        "Dear @" + username + ",<br><br>We are notifying you that there is a new special offer! " +
                                rentable.getName() + " is available for the period of "
                                + discountDTO.getStartDateTime().replace("T", " ") + " to " + discountDTO.getEndDateTime().replace("T", " ") +
                                ". Cost of this reservation is " + discountDTO.getPrice() + " for " + discountDTO.getCapacity() + " people. " +
                                "Hurry up and don't miss this opportunity!<br>" +
                                "You are subscribed to " + rentable.getName() + " so that is why you got this email. If you " +
                                "want to unsubscribe, please go to rentable page and click 'Unsubscribe' or go to your account settings." +
                                "<br><br>Sincerely,<br> Rivera.");
            }
            catch (Exception e) {
                System.out.println("email not sent!");
            }
        }
    }

    private Discount discountDTOToDiscount(DiscountDTO discountDTO, Rentable rentable){
        Discount discount = new Discount();
        discount.setPrice(discountDTO.getPrice());
        System.out.println(discountDTO.getStartDateTime()+"DTO");
        discount.setStartDateTime(LocalDateTime.parse(discountDTO.getStartDateTime().split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        discount.setEndDateTime(LocalDateTime.parse(discountDTO.getEndDateTime().split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println(discount.getStartDateTime()+"DISCOUNT");
        discount.setCapacity(discountDTO.getCapacity());
        tagService.addTagsIfNotPresent(discountDTO.getTags());
        discount.setTags(tagService.getTagsByNames(discountDTO.getTags()));
        discount.setAdditionalServices(discountDTO.getAdditionalServices());
        discount.setReserved(discount.isReserved());
        discount.setRentable(rentable);
        return discount;
    }

    public void setDiscountInactive(String discountId) {
        if(discountId == null || discountId.isEmpty()){
            return;
        }
        Discount discount = discountRepository.getById(Integer.parseInt(discountId));
        discount.setReserved(true);
        discountRepository.save(discount);
    }

    public void deleteDiscount(Integer id) {
        discountRepository.delete(discountRepository.getById(id));
    }
}
