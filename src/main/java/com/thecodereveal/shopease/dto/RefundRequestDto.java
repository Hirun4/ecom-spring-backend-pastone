package com.thecodereveal.shopease.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RefundRequestDto {
    private Long orderId;
    private String reason;
    private String bankAccountNumber;
    private String bankBranch;
    private String bankName;
    private Double refundAmount;
    private List<String> imageUrls;
}