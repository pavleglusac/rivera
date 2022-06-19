package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DiscountDTO {

    String startDateTime;
    String endDateTime;
    Integer capacity;
    Double price;
    List<String> tags;
    Integer rentableId;
    boolean reserved;
}
