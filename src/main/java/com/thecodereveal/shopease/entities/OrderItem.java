package com.thecodereveal.shopease.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer item_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "origin_country", length = 100)
    private String origin_country;

    @Column(name = "size", length = 50, nullable = false)
    private String size;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "buying_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buying_price;

    @Column(name = "selling_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal selling_price;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "buying_price_code", length = 255)
    private String buying_price_code;

    
    
}