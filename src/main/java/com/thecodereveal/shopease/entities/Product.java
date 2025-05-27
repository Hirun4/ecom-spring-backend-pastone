package com.thecodereveal.shopease.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private String image_url;
    
    @Column
    private String category;
    
    @Column
    private String origin_country;
    
    @Column
    private int stock_quantity;

    @Column
    private String stock_status;

    @Column
    private String buying_price_code;

    @Column(nullable = false, updatable = false,columnDefinition = "DATETIME")
    private java.util.Date created_at;
    
    @Column(nullable = false, columnDefinition = "DATETIME")
    private java.util.Date updated_at;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product_stock> stocks;



    
    
}
