package com.thecodereveal.shopease.entities;

import jakarta.persistence.*;
import lombok.*;

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
}