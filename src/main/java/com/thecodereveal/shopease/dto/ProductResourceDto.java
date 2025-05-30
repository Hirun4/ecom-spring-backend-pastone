package com.thecodereveal.shopease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResourceDto {
    private UUID id;
    private String name;
    private String type;
    private String url;
    private Boolean isPrimary;
    private int stock_id;
    private int product_id;
    private String size;
    private int quantity;
}
