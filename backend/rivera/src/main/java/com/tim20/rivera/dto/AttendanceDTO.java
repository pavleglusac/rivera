package com.tim20.rivera.dto;

import java.time.LocalDateTime;

public class AttendanceDTO {
    LocalDateTime timestamp;
    Integer count;

    public AttendanceDTO(LocalDateTime timestamp, Integer count) {
        this.timestamp = timestamp;
        this.count = count;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
