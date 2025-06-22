package com.thecodereveal.shopease.dto;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private Long productId;
    private UUID userId;
    private int rating;
    private String comment;
    private String userName;
    private String createdAt;
    // Getters and setters
}