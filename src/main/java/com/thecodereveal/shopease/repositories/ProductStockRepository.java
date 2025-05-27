package com.thecodereveal.shopease.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stripe.model.Product;
import com.thecodereveal.shopease.entities.Product_stock;

public interface ProductStockRepository extends JpaRepository<Product_stock, Integer> {
    
    List<Product_stock> findByProduct(Product product);
    
}
