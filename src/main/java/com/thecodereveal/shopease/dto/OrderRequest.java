package com.thecodereveal.shopease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private int order_id;
    private String tracking_number;
    private String customer_name;
    private String address;
    private String phone_number;
    private String district;
    private String delivery_method;
    private String status;
    private String return_reason;
    private BigDecimal delivery_fee;
    private java.util.Date created_at;
    private java.util.Date updated_at;
    private List<OrderItemRequest> orderItems;
    private String payment_method;
}
