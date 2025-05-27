package com.thecodereveal.shopease.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {

    private Integer item_id;
    private Integer order_id;
    private Integer product_id;
    private String origin_country;
    private String size;
    private Integer quantity;
    private BigDecimal buying_price;
    private BigDecimal selling_price;
    private BigDecimal discount;
    private String buying_price_code;
    private List<ProductDto> products;
}
