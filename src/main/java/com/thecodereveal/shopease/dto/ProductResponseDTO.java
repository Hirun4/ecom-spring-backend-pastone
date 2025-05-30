package com.thecodereveal.shopease.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductResponseDTO{
    private int product_id;
    private String name;
    private String description;
    private double price;
    private String image_url;
    private String category;
    private String origin_country;
    private int stock_quantity;
    private String stock_status;
    private String buying_price_code;
    private Date created_at;
    private Date updated_at;
    private List<ProductResourceDto> stock;
}
