package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewProfileDTO {
    private Integer id;
    private Double score;
    private LocalDateTime posted;
    private String text;
    private ClientRentableDto client;
}
