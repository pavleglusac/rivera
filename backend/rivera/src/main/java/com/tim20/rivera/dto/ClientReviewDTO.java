package com.tim20.rivera.dto;

import com.tim20.rivera.model.Owner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ClientReviewDTO {
    private Integer id;
    private LocalDateTime posted;
    private String text;
    private String ownerPicture;
    private String ownerName;
    private String ownerSurname;
}
