package com.tim20.rivera.dto;

import com.tim20.rivera.model.ReservationReportType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationReportDTO {

    Integer id;
    ReservationReportType reservationReportType;
    Boolean sanction;
    Boolean showedUp;
    String text;
    LocalDateTime posted;
}
