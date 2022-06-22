package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OwnerReservationDTO {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    Boolean cancelled;
    RentableDTO rentable;
    ClientRentableDto client;
    Double price;
    ReservationReportDTO report;
    List<String> additionalServices;
    Integer reservationId;

    public boolean isUpcoming() {
        return !this.startDateTime.isBefore(LocalDateTime.now());
    }

    public boolean isBetween(LocalDate date) {
        return !this.startDateTime.toLocalDate().isAfter(date) && !this.endDateTime.toLocalDate().isBefore(date);
    }
}
