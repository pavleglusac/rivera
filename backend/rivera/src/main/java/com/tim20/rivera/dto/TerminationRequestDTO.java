package com.tim20.rivera.dto;

import lombok.Data;

@Data
public class TerminationRequestDTO {
    private String username;
    private String description;
    private int requestId;
}
