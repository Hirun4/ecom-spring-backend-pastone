// src/main/java/com/thecodereveal/shopease/repositories/ProductCodesRepository.java
package com.thecodereveal.shopease.repositories;

import com.thecodereveal.shopease.entities.Product_codes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCodesRepository extends JpaRepository<Product_codes, Integer> {
    Product_codes findByCode(String code);
}