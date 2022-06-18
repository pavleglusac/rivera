package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ClientReservationDTO {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    Boolean cancelled;
    EntityReservationDTO entity;
    Integer rentableId;
    Integer reservationId;
    Double price;
    List<String> additionalServices;

    public boolean isUpcoming() {
        return !this.startDateTime.isBefore(LocalDateTime.now());
    }

    public boolean isBetween(LocalDate date) {
        return !this.startDateTime.toLocalDate().isAfter(date) && !this.endDateTime.toLocalDate().isBefore(date);
    }
}
