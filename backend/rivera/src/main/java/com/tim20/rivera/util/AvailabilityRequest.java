package com.tim20.rivera.util;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AvailabilityRequest {
    private Integer rentableId;
    private List<List<String>> patterns;
    private String repeat;
    private LocalDateTime selectedStartDate;
    private LocalDateTime selectedEndDate;
    private Boolean addition;

    @Override
    public String toString() {
        return "AvailabilityRequest{" +
                "patterns=" + patterns +
                ", repeat='" + repeat + '\'' +
                ", selectedStartDate=" + selectedStartDate +
                ", selectedEndDate=" + selectedEndDate +
                ", addition=" + addition +
                '}';
    }
}
