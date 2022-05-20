package com.tim20.rivera.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class ReservationSearch {
    String search;
    String date;
    // can be 'upcoming', 'past', 'all' or 'date'
    String upcoming;
}
