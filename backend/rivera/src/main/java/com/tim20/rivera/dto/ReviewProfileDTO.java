package com.tim20.rivera.dto;

import com.tim20.rivera.model.ReviewStatus;
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
    private OwnerDTO owner;
    private ReviewStatus status;
}
