package com.thecodereveal.shopease.dto;

public class ProductOrderCountDto {
    private Integer product_id;
    private Long orderCount;

    public ProductOrderCountDto(Integer product_id, Long orderCount) {
        this.product_id = product_id;
        this.orderCount = orderCount;
    }

    // Getters and setters
    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer productId) {
        this.product_id = product_id;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }
}
