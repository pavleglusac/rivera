package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationDTO {
    LocalDateTime start;
    LocalDateTime end;
    Boolean cancelled;
    ClientRentableDto client;
    Integer id;
    RentableDTO rentable;
    ReservationReportDTO report;
    Double price;
    List<String> additionalServices;
}
