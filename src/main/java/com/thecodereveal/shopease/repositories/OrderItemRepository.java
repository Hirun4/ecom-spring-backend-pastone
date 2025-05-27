package com.thecodereveal.shopease.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thecodereveal.shopease.dto.ProductOrderCountDto;
import com.thecodereveal.shopease.entities.OrderItem;
import com.thecodereveal.shopease.entities.Product;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    // 1. Just products (without count)
    @Query("SELECT oi.product FROM OrderItem oi " +
           "GROUP BY oi.product " +
           "ORDER BY COUNT(oi.product) DESC")
    List<Product> findMostOrderedProducts();

    // 2. Products with count using DTO
    @Query("SELECT new com.thecodereveal.shopease.dto.ProductOrderCountDto(oi.product.product_id, COUNT(oi)) " +
       "FROM OrderItem oi " +
       "GROUP BY oi.product.product_id " +
       "ORDER BY COUNT(oi) DESC")
    List<ProductOrderCountDto> findProductOrderCounts();

}
