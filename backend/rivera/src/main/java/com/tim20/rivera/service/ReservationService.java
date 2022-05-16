package com.tim20.rivera.service;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.Reservation;
import com.tim20.rivera.repository.ClientRepository;
import com.tim20.rivera.repository.CottageRepository;
import com.tim20.rivera.repository.RentableRepository;
import com.tim20.rivera.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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


    private List<ReservationDTO> getReservationsOfOwner(String ownerUsername) {
        return reservationRepository.findByRentableOwnerUsername(ownerUsername).stream().map(this::reservationToDto).collect(Collectors.toList());
    }

}
