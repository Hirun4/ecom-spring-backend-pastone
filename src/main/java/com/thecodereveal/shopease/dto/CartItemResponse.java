package com.thecodereveal.shopease.dto;

import com.thecodereveal.shopease.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import  lombok.Builder;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemResponse {
    private Integer id;
    private Integer quantity;
    private String size;
    private ProductResponseDTO product;

}
