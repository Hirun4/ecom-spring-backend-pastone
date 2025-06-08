package com.thecodereveal.shopease.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product_codes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", length = 100, nullable = false, unique = true)
    private String code;

    @Column(name = "buying_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingPrice;
}