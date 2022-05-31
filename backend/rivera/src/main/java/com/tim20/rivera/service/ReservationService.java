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
import org.apache.tomcat.jni.Local;
import com.tim20.rivera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private RulesRepository rulesRepository;

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
        dto.setPrice(reservation.getPrice());
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
        clientReservationDTO.setRentableId(reservation.getRentable().getId());
        clientReservationDTO.setStartDateTime(reservation.getStartDateTime());
        clientReservationDTO.setEndDateTime(reservation.getEndDateTime());
        clientReservationDTO.setPrice(reservation.getPrice());
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

    public Reservation addReservation(Client client, Integer rentableId, LocalDateTime start, LocalDateTime end, Double price) {
        Reservation reservation = new Reservation();
        reservation.setPrice(price);
        reservation.setRentable(rentableRepository.getById(rentableId));
        reservation.setStartDateTime(start);
        reservation.setEndDateTime(end);
        reservation.setCancelled(false);
        reservation.setClient(client);
        Double ownerPercentage = rentableRepository.getById(rentableId).getOwner().getCategory().getPercentage() + (1 - rulesRepository.findAll().get(0).getIncomePercentage());
        reservation.setOwnerIncomePercentage(ownerPercentage);
        reservationRepository.save(reservation);
        return reservation;
    }

    public Double calculatePriceForReservation(Client client, Integer rentableId, LocalDateTime start, LocalDateTime end) {
        System.out.println("START: " + start);
        System.out.println("END: " + end);
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
        double pricePerDay = days * rentable.getCurrentPricelist().getPricePerDay();
        System.out.println("PRICE PER DAY:" + pricePerDay);
        Double dicount = clientService.calculateDiscount(client);
        return min(pricePerHour, pricePerDay) * (100 - dicount) / 100;
    }

    public OwnerReservationDTO reservationDTOToOwnerReservationDTO(ReservationDTO reservation) {
        OwnerReservationDTO dto = new OwnerReservationDTO();
        dto.setStartDateTime(reservation.getStart());
        dto.setEndDateTime(reservation.getEnd());
        dto.setRentable(reservation.getRentable());
        dto.setCancelled(reservation.getCancelled());
        dto.setClient(reservation.getClient());
        dto.setReservationId(reservation.getId());
        dto.setReport(reservation.getReport());
        dto.setPrice(reservation.getPrice());
        return dto;
    }

    private List<OwnerReservationDTO> getAllReservationsOfOwner(String ownerUsername) {
        return this.getReservationsOfOwner(ownerUsername).stream().map(this::reservationDTOToOwnerReservationDTO).collect(Collectors.toList());
    }

    public List<OwnerReservationDTO> searchAllReservationsForOwner(String username, ReservationSearch search) {
        List<OwnerReservationDTO> reservations = this.getAllReservationsOfOwner(username);
        return getOwnerReservationsByDate(reservations.stream().
                filter(a -> a.getRentable().getName().toLowerCase().contains(search.getSearch().toLowerCase())).toList(), search);
    }

    private List<OwnerReservationDTO> getOwnerReservationsByDate(List<OwnerReservationDTO> reservations, ReservationSearch search) {
        if(search.getUpcoming().equals("upcoming"))
            return reservations.stream().filter(OwnerReservationDTO::isUpcoming).toList();
        if(search.getUpcoming().equals("past"))
            return reservations.stream().filter(a-> !a.isUpcoming()).toList();
        if(search.getUpcoming().equals("date")) {
            LocalDate date = LocalDate.parse(search.getDate());
            return reservations.stream().filter(a -> a.isBetween(date)).toList();
        }
        return reservations;
    }

    public List<ReservationDTO> searchReservationsForEntity(Integer id, SearchParams searchParams) {
        List<Reservation> reservations = reservationRepository.findByRentableId(id);
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        String search = searchParams.getSearch().toLowerCase();
        for (Reservation r : reservations) {
            if (searchClient(r.getClient(), search) && dateInbetween(r.getStartDateTime(), r.getEndDateTime(), searchParams.getStart(), searchParams.getEnd()))
                reservationDTOS.add(reservationToDto(r));
        }
        return reservationDTOS;
    }

    private boolean dateInbetween(LocalDateTime startDateTime, LocalDateTime endDateTime, String searchStart, String searchEnd) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(!Objects.equals(searchStart, "")) {
            LocalDateTime start = LocalDate.parse(searchStart, formatter).atStartOfDay();
            if(startDateTime.isBefore(start) || endDateTime.isBefore(start))
                return false;
        }
        if(!Objects.equals(searchEnd, "")) {
            LocalDateTime end = LocalDate.parse(searchEnd, formatter).atStartOfDay();
            return !startDateTime.isAfter(end) && !endDateTime.isAfter(end);
        }
        return true;
    }

    public boolean searchClient(Client client, String search) {
        String username = client.getUsername().toLowerCase();
        String name = client.getName().toLowerCase();
        String surname = client.getSurname().toLowerCase();
        return username.contains(search) || name.contains(search) || surname.contains(search);
    }
}