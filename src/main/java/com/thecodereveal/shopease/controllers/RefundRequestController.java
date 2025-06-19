package com.thecodereveal.shopease.controllers;

import com.thecodereveal.shopease.dto.RefundRequestDto;
import com.thecodereveal.shopease.dto.RefundRequestResponseDto;
import com.thecodereveal.shopease.services.RefundRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refund-requests")
@CrossOrigin
public class RefundRequestController {

    @Autowired
    private RefundRequestService refundRequestService;

    @PostMapping
    public ResponseEntity<RefundRequestResponseDto> createRefundRequest(@RequestBody RefundRequestDto refundRequestDto) {
        RefundRequestResponseDto response = refundRequestService.createRefundRequest(refundRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // src/main/java/com/thecodereveal/shopease/controllers/RefundRequestController.java
    @GetMapping("/order/{orderId}")
    public ResponseEntity<RefundRequestResponseDto> getRefundRequestByOrderId(@PathVariable Long orderId) {
        RefundRequestResponseDto response = refundRequestService.getRefundRequestByOrderId(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}