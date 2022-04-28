package com.tim20.rivera.util;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Availability {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
