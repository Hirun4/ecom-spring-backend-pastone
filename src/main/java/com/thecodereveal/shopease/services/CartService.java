package com.thecodereveal.shopease.services;

import com.thecodereveal.shopease.dto.CartItemRequest;
import com.thecodereveal.shopease.dto.CartItemResponse;
import com.thecodereveal.shopease.entities.CartItem;
import com.thecodereveal.shopease.entities.Product;
import com.thecodereveal.shopease.repositories.CartItemRepository;
import com.thecodereveal.shopease.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartItem addToCart(String userIdentifier, CartItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByUserIdentifierAndProductIdAndSize(userIdentifier, request.getProductId(),
                        request.getSize())
                .map(item -> {
                    item.setQuantity(item.getQuantity() + request.getQuantity());
                    return item;
                })
                .orElse(CartItem.builder()
                        .userIdentifier(userIdentifier)
                        .product(product)
                        .size(request.getSize())
                        .quantity(request.getQuantity())
                        .build());

        return cartItemRepository.save(cartItem);
    }

    public List<CartItemResponse> getCart(String userIdentifier) {
        return cartItemRepository.findByUserIdentifier(userIdentifier);
    }

    public void clearCart(String userIdentifier) {
        cartItemRepository.deleteByUserIdentifier(userIdentifier);
    }
}
