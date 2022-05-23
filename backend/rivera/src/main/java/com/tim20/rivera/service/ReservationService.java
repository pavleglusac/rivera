package com.tim20.rivera.service;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.Reservation;
import com.tim20.rivera.repository.ClientRepository;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.min;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CottageRepository cottageRepository;
    @Autowired
    private CottageService cottageService;
    @Autowired
    private RentableService rentableService;
    @Autowired
    private RentableRepository rentableRepository;

    public List<ReservationDTO> getReservationsByOwner() {
        return null;
    }

    public List<ReservationDTO> searchReservationsForOwner(SearchParams searchParams) {
        List<ReservationDTO> reservationDTOS = checkTags(this.getReservationsOfOwner(searchParams.getOwnerUsername()), searchParams.getTags());
        return sortReservations(searchParams.getOrderBy(), reservationDTOS.stream().limit(searchParams.getNumberOfResults())
                .filter
                        (a -> rentableService.rentableToDto(rentableRepository.findById(a.getRentable().getId()).get()).getName().toLowerCase().
                                contains(searchParams.getSearch().toLowerCase()))
                .collect(Collectors.toList()));
    }


    private List<ReservationDTO> checkTags(List<ReservationDTO> reservationDTOS, List<String> tags) {
        List<ReservationDTO> correctRentables = new ArrayList<>();
        for (ReservationDTO r : reservationDTOS) {
             RentableDTO c = rentableService.rentableToDto(rentableRepository.findById(r.getRentable().getId()).get());
             if ((c.getTags().containsAll(tags) || tags.size() == 0))
                correctRentables.add(r);
            }
        return correctRentables;
    }


    public List<ReservationDTO> sortReservations(String sortParam, List<ReservationDTO> reservationDTOS) {
        return switch (sortParam) {
            case "date-a" -> reservationDTOS.stream().sorted(Comparator.comparing(ReservationDTO::getStart)).toList();
            case "date-d" -> reservationDTOS.stream().sorted(Comparator.comparing(ReservationDTO::getStart, Comparator.reverseOrder())).toList();
            default -> reservationDTOS;
        };
    }

    private ReservationDTO reservationToDto(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setStart(reservation.getStartDateTime());
        dto.setEnd(reservation.getEndDateTime());
        dto.setRentable(rentableService.rentableToDto(reservation.getRentable()));
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
            dto.setReport(reservationReportDTO);
        }
        return dto;
    }

    private ClientReservationDTO reservationToClientReservationDTO(Reservation reservation) {
        ClientReservationDTO clientReservationDTO = new ClientReservationDTO();
        clientReservationDTO.setCancelled(reservation.getCancelled());
        clientReservationDTO.setStartDateTime(reservation.getStartDateTime());
        clientReservationDTO.setEndDateTime(reservation.getEndDateTime());
        clientReservationDTO.setEntity(new EntityReservationDTO(reservation.getRentable()));
        return clientReservationDTO;
    }

    public List<ClientReservationDTO> getClientReservations(String username) {
        return reservationRepository.findByClientUsername(username).stream().map(this::reservationToClientReservationDTO).collect(Collectors.toList());
    }

    public List<ClientReservationDTO> getReservations(String username, ReservationSearch search) {
        List<ClientReservationDTO> reservations = this.getClientReservations(username);
        return getReservationsByDate(reservations.stream().
                filter(a -> a.getEntity().getName().toLowerCase().contains(search.getSearch().toLowerCase())).toList(), search);
    }

    private List<ClientReservationDTO> getReservationsByDate(List<ClientReservationDTO> reservations, ReservationSearch search) {
        if(search.getUpcoming().equals("upcoming"))
            return reservations.stream().filter(ClientReservationDTO::isUpcoming).toList();
        if(search.getUpcoming().equals("past"))
            return reservations.stream().filter(a-> !a.isUpcoming()).toList();
        if(search.getUpcoming().equals("date")) {
            LocalDate date = LocalDate.parse(search.getDate());
            return reservations.stream().filter(a -> a.isBetween(date)).toList();
        }
        return reservations;
    }

    private List<ReservationDTO> getReservationsOfOwner(String ownerUsername) {
        return reservationRepository.findByRentableOwnerUsername(ownerUsername).stream().map(this::reservationToDto).collect(Collectors.toList());
    }

    public Reservation addReservation(Client client, Integer rentableId, LocalDateTime start, LocalDateTime end) {
        Reservation reservation = new Reservation();
        reservation.setPrice(calculatePriceForReservation(client, rentableId, start, end));
        reservation.setRentable(rentableRepository.getById(rentableId));
        reservation.setStartDateTime(start);
        reservation.setEndDateTime(end);
        reservation.setCancelled(false);
        reservation.setClient(client);
        reservationRepository.save(reservation);
        return reservation;
    }

    public Double calculatePriceForReservation(Client client, Integer rentableId, LocalDateTime start, LocalDateTime end) {
        long hours = ChronoUnit.HOURS.between(start, end);
        if (ChronoUnit.MINUTES.between(start, end) % 60 > 0)
            hours++;
        System.out.println("HOURS:" + hours);
        long days = ChronoUnit.DAYS.between(start, end);
        if (hours % 24 > 0)
            days++;
        System.out.println("DAYS:" +days);
        Rentable rentable = rentableRepository.getById(rentableId);
        double pricePerHour = hours * rentable.getCurrentPricelist().getPricePerHour();
        System.out.println("PRICE PER HOUR:" +pricePerHour);
        double pricePerDay = days * rentable.getCurrentPricelist().getPricePerHour();
        System.out.println("PRICE PER DAY:" + pricePerDay);
        // add discount for client
        return min(pricePerHour, pricePerDay);
    }
}