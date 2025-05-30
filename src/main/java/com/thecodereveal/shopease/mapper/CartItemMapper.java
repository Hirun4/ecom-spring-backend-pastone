package com.thecodereveal.shopease.mapper;

import com.thecodereveal.shopease.dto.CartItemResponse;
import com.thecodereveal.shopease.dto.ProductResponseDTO;
import com.thecodereveal.shopease.entities.CartItem;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartItemMapper {
    public CartItemResponse toDto(CartItem cartItem) {
        ProductResponseDTO productDTO = ProductResponseDTO.builder()
                .product_id(cartItem.getProduct().getProduct_id())
                .name(cartItem.getProduct().getName())
                .description(cartItem.getProduct().getDescription())
                .price(cartItem.getProduct().getPrice().doubleValue())  // Convert BigDecimal to double
                .image_url(cartItem.getProduct().getImage_url())
                .category(cartItem.getProduct().getCategory())
                .origin_country(cartItem.getProduct().getOrigin_country())
                .stock_quantity(cartItem.getProduct().getStock_quantity())
                .stock_status(cartItem.getProduct().getStock_status())
                .build();

        return CartItemResponse.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .size(cartItem.getSize())
                .product(productDTO)
                .build();
    }

    public List<CartItemResponse> toDtoList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}