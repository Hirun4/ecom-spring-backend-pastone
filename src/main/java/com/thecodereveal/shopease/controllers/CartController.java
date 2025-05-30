package com.thecodereveal.shopease.controllers;

import com.stripe.model.Product;
import com.thecodereveal.shopease.dto.CartItemRequest;
import com.thecodereveal.shopease.dto.CartItemResponse;
import com.thecodereveal.shopease.dto.ProductResponseDTO;
import com.thecodereveal.shopease.dto.ProductResourceDto;
import com.thecodereveal.shopease.dto.ProductStockDto;
import com.thecodereveal.shopease.entities.CartItem;
import com.thecodereveal.shopease.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{userIdentifier}")
    public ResponseEntity<CartItemResponse> addToCart(@PathVariable String userIdentifier,
            @RequestBody CartItemRequest request) {
        CartItem item = cartService.addToCart(userIdentifier, request);

        // Filter stocks by requested size only
        List<ProductStockDto> filteredStocks = item.getProduct().getStocks().stream()
                .filter(stock -> stock.getSize().equals(request.getSize()))
                .map(stock -> ProductStockDto.builder()
                        .stock_id(stock.getStock_id())
                        .product_id(stock.getProduct().getProduct_id())
                        .size(stock.getSize())
                        .quantity(stock.getQuantity())
                        .build())
                .collect(Collectors.toList());

        List<ProductResourceDto> filteredResourceStocks = filteredStocks.stream()
                .map(stockDto -> ProductResourceDto.builder()
                        .stock_id(stockDto.getStock_id())
                        .product_id(stockDto.getProduct_id())
                        .size(stockDto.getSize())
                        .quantity(stockDto.getQuantity())
                        .build())
                .collect(Collectors.toList());

        ProductResponseDTO productDTO = ProductResponseDTO.builder()
                .product_id(item.getProduct().getProduct_id())
                .name(item.getProduct().getName())
                .description(item.getProduct().getDescription())
                .price(item.getProduct().getPrice().doubleValue())
                .image_url(item.getProduct().getImage_url())
                .category(item.getProduct().getCategory())
                .origin_country(item.getProduct().getOrigin_country())
                .stock_quantity(item.getProduct().getStock_quantity())
                .stock_status(item.getProduct().getStock_status())
                .buying_price_code(item.getProduct().getBuying_price_code())
                .created_at(item.getProduct().getCreated_at())
                .updated_at(item.getProduct().getUpdated_at())
                .stock(filteredResourceStocks)
                .build();

        CartItemResponse response = new CartItemResponse(
                item.getId(),
                item.getQuantity(),
                item.getSize(),
                productDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userIdentifier}")
    public ResponseEntity<List<CartItemResponse>> getCart(@PathVariable String userIdentifier) {
        return ResponseEntity.ok(cartService.getCart(userIdentifier));
    }

    @DeleteMapping("/clear/{userIdentifier}")
    public ResponseEntity<Void> clearCart(@PathVariable String userIdentifier) {
        cartService.clearCart(userIdentifier);
        return ResponseEntity.ok().build();
    }
}