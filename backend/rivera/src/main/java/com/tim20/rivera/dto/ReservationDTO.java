package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDTO {
    LocalDateTime start;
    LocalDateTime end;
    Boolean cancelled;
    ClientRentableDto client;
    RentableDTO rentable;
}
