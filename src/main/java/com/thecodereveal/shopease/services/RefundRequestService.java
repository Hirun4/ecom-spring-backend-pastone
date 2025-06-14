package com.thecodereveal.shopease.services;

import com.thecodereveal.shopease.dto.RefundRequestDto;
import com.thecodereveal.shopease.dto.RefundRequestResponseDto;
import com.thecodereveal.shopease.entities.RefundRequest;
import com.thecodereveal.shopease.repositories.RefundRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefundRequestService {

    @Autowired
    private RefundRequestRepository refundRequestRepository;

    public RefundRequestResponseDto createRefundRequest(RefundRequestDto dto) {
        RefundRequest refundRequest = RefundRequest.builder()
                .orderId(dto.getOrderId())
                .reason(dto.getReason())
                .bankAccountNumber(dto.getBankAccountNumber())
                .bankBranch(dto.getBankBranch())
                .bankName(dto.getBankName())
                .refundAmount(dto.getRefundAmount())
                .status("PENDING")
                .createdAt(new Date())
                .imageUrls(dto.getImageUrls())
                .build();

        RefundRequest saved = refundRequestRepository.save(refundRequest);

        RefundRequestResponseDto response = new RefundRequestResponseDto();
        response.setId(saved.getId());
        response.setOrderId(saved.getOrderId());
        response.setReason(saved.getReason());
        response.setBankAccountNumber(saved.getBankAccountNumber());
        response.setBankBranch(saved.getBankBranch());
        response.setBankName(saved.getBankName());
        response.setRefundAmount(saved.getRefundAmount());
        response.setStatus(saved.getStatus());
        response.setCreatedAt(saved.getCreatedAt());
        response.setImageUrls(saved.getImageUrls());
        return response;
    }
}