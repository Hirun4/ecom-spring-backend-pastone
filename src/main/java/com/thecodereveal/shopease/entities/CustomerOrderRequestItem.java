package com.thecodereveal.shopease.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customer_order_request_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderRequestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_order_request_id")
    private CustomerOrderRequest customerOrderRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String size;
    private Integer quantity;

    @Column(name = "origin_country", length = 100)
    private String origin_country;

    @Column(name = "buying_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buying_price;

    @Column(name = "selling_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal selling_price;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "buying_price_code", length = 255)
    private String buying_price_code;

    @Column(name = "promo_price",length = 100)
    private int promo_price;

    @Column(name = "final_price", length = 100)
    private int final_price;

    @Column(name = "phone_number", length = 20)
    private String phone_number;

}