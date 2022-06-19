package com.tim20.rivera.dto;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class DiscountProfileDTO {
    Integer id;
    LocalDateTime start;
    LocalDateTime end;
    Integer capacity;
    Double price;
    List<String> tags;
    boolean reserved;
}
