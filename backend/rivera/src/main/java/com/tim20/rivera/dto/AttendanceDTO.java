package com.tim20.rivera.dto;

import java.time.LocalDateTime;

public class AttendanceDTO {
    LocalDateTime timestamp;
    Integer count;

    public AttendanceDTO(LocalDateTime timestamp, Integer count) {
        this.timestamp = timestamp;
        this.count = count;
    }
}
