package com.thecodereveal.shopease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockDto {
    
    private int stock_id;
    private int product_id;
    private String size;
    private int quantity;

}
