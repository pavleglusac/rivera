package com.tim20.rivera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResolve {
    int reviewId;
    String responseText;
    boolean allowed;
}
