package com.tim20.rivera.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchParams {
    String search;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime start;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime end;
    String orderBy;
    List<String> tags;
    int numberOfResults;
    String ownerUsername;
}
