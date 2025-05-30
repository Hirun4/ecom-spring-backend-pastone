package com.thecodereveal.shopease.repositories;

import com.thecodereveal.shopease.dto.CartItemRequest;
import com.thecodereveal.shopease.dto.CartItemResponse;
import com.thecodereveal.shopease.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUserIdentifier(String userIdentifier);

    @Query("SELECT c FROM CartItem c WHERE c.userIdentifier = :userIdentifier AND c.product.product_id = :productId AND c.size = :size")
    Optional<CartItem> findByUserIdentifierAndProductIdAndSize(
        @Param("userIdentifier") String userIdentifier,
        @Param("productId") int productId,
        @Param("size") String size
    );

    void deleteById(Integer id);
}
