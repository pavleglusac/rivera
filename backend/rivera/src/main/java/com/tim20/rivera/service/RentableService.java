package com.tim20.rivera.service;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.dto.RentableDTO;
import com.tim20.rivera.dto.ReservationDTO;
import com.tim20.rivera.dto.ReservationReportDTO;
import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.OwnerRepository;
import com.tim20.rivera.repository.RentableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentableService {

    @Autowired
    private RentableRepository rentableRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ClientService clientService;

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

    public List<ReservationDTO> getReservations(Integer id) {
        Optional<Rentable> rentable = rentableRepository.findById(id);
        if (rentable.isEmpty()) return new ArrayList<>();
        return rentable.get().getReservations().stream().map(this::reservationToDto).collect(Collectors.toList());
    }

    private ReservationDTO reservationToDto(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setStart(reservation.getStartDateTime());
        dto.setEnd(reservation.getEndDateTime());
        dto.setCancelled(reservation.getCancelled());
        dto.setClient(clientService.clientToCRDto(reservation.getClient()));
        dto.setId(reservation.getId());
        dto.setReport(null);
        if(reservation.getReservationReport() != null) {
            ReservationReportDTO reservationReportDTO = new ReservationReportDTO();
            reservationReportDTO.setId(reservation.getReservationReport().getId());
            reservationReportDTO.setReservationReportType(reservation.getReservationReport().getReservationReportType());
            reservationReportDTO.setShowedUp(reservation.getReservationReport().getShowedUp());
            reservationReportDTO.setSanction(reservation.getReservationReport().getSanction());
            reservationReportDTO.setText(reservation.getReservationReport().getText());
            reservationReportDTO.setPosted(reservation.getReservationReport().getPosted());
            dto.setReport(reservationReportDTO);
        }
        return dto;
    }

    public void addReview(Rentable rentable, Client client, String reviewText, double rating, ReviewType type) {
        Review review = new Review();
        review.setRentable(rentable);
        review.setClient(client);
        review.setText(reviewText);
        review.setPosted(LocalDateTime.now());
        review.setScore(rating);
        review.setStatus(ReviewStatus.ACCEPTED);
        review.setType(type);
        rentable.getReviews().add(review);
        rentableRepository.save(rentable);
    }

    public List<Review> getReviews(Integer id) {
        return rentableRepository.findById(id).get().getReviews();
    }

    public void delete(Integer id) {
        Optional<Rentable> optionalRentable = rentableRepository.findById(id);
        if(optionalRentable.isEmpty()) return;
        rentableRepository.delete(optionalRentable.get());
    }
}