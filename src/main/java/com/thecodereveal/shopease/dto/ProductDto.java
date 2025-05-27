package com.thecodereveal.shopease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import com.thecodereveal.shopease.entities.Product_stock;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private int product_id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image_url;
    private String category;
    private String origin_country;
    private int stock_quantity;
    private String stock_status;
    private String buying_price_code;
    private java.util.Date created_at;
    private java.util.Date updated_at;
    private List<ProductStockDto> stocks;

}
