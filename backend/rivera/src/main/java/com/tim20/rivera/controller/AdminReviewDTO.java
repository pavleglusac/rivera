package com.tim20.rivera.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminReviewDTO {
    private String reviewedName;
    private String reviewedImage;
    private String clientName;
    private String clientSurname;
    private String clientImage;
    private Integer id;
    private Double score;
    private String text;
    private boolean complaint;
}
