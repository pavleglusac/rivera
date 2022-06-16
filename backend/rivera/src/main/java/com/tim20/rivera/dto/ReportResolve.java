package com.tim20.rivera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportResolve {
    private int reportId;
    private String responseText;
    private boolean assignPenalty;
}
