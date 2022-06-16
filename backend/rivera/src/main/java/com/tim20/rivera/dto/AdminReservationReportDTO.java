package com.tim20.rivera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationReportDTO {
    private Integer id;
    private String ownerName;
    private String ownerImage;
    private String clientName;
    private String clientImage;
    private String text;
    private String type;
    private boolean showedUp;
    private boolean sanction;
}
