package com.thecodereveal.shopease.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {
    private Integer productId;
    private String size;
    private Integer quantity;
}