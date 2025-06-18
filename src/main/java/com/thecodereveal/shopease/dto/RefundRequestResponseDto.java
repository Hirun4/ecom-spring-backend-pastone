package com.thecodereveal.shopease.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class RefundRequestResponseDto {
    private int id;
    private Long orderId;
    private String reason;
    private String bankAccountNumber;
    private String bankBranch;
    private String bankName;
    private Double refundAmount;
    private String status;
    private Date createdAt;
    private List<String> imageUrls;
}