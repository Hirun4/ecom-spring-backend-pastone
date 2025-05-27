package com.thecodereveal.shopease.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product_stock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stock_id;

    // @Column(nullable = false)
    //  private int product_id;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)  // Maps to product_id in the DB
    private Product product;

    private String size;
    private int quantity;
}
