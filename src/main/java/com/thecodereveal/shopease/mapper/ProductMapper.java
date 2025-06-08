package com.thecodereveal.shopease.mapper;

import com.thecodereveal.shopease.dto.OrderItemRequest;
import com.thecodereveal.shopease.dto.OrderRequest;
import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.dto.ProductResourceDto;
import com.thecodereveal.shopease.dto.ProductVariantDto;
import com.thecodereveal.shopease.entities.*;
import com.thecodereveal.shopease.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @Autowired
    private CategoryService categoryService;

    public Product mapToProductEntity(ProductDto productDto){
        Product product = new Product();
        if(productDto.getProduct_id() != 0){
            product.setProduct_id(productDto.getProduct_id());
        }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImage_url(productDto.getImage_url());
        product.setCategory(productDto.getCategory());
        product.setOrigin_country(productDto.getOrigin_country());
        product.setStock_quantity(productDto.getStock_quantity());
        product.setStock_status(productDto.getStock_status());
        product.setBuying_price_code(productDto.getBuying_price_code());
        product.setCreated_at(productDto.getCreated_at());
        product.setUpdated_at(productDto.getUpdated_at());
        return product;
    }

    private List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {
        return productResources.stream().map(productResourceDto -> {
            Resources resources= new Resources();
            if(null != productResourceDto.getId()){
                resources.setId(productResourceDto.getId());
            }
            resources.setName(productResourceDto.getName());
            resources.setType(productResourceDto.getType());
            resources.setUrl(productResourceDto.getUrl());
            resources.setIsPrimary(productResourceDto.getIsPrimary());
            resources.setProduct(product);
            return resources;
        }).collect(Collectors.toList());
    }

    private List<ProductVariant> mapToProductVariant(List<ProductVariantDto> productVariantDtos, Product product){
        return productVariantDtos.stream().map(productVariantDto -> {
            ProductVariant productVariant = new ProductVariant();
            if(null != productVariantDto.getId()){
                productVariant.setId(productVariantDto.getId());
            }
            productVariant.setColor(productVariantDto.getColor());
            productVariant.setSize(productVariantDto.getSize());
            productVariant.setStockQuantity(productVariantDto.getStockQuantity());
            productVariant.setProduct(product);
            return productVariant;
        }).collect(Collectors.toList());
    }

    public List<ProductDto> getProductDtos(List<Product> products) {
        return products.stream().map(this::mapProductToDto).toList();
    }

    public ProductDto mapProductToDto(Product product) {
        return ProductDto.builder()
                .product_id(product.getProduct_id())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image_url(product.getImage_url())
                .category(product.getCategory())
                .origin_country(product.getOrigin_country())
                .stock_quantity(product.getStock_quantity())
                .stock_status(product.getStock_status())
                .buying_price_code(product.getBuying_price_code())
                .created_at(product.getCreated_at())
                .updated_at(product.getUpdated_at())
                .build();
    }

    private String getProductThumbnail(List<Resources> resources) {
        return resources.stream().filter(Resources::getIsPrimary).findFirst().orElse(null).getUrl();
    }

    public List<ProductVariantDto> mapProductVariantListToDto(List<ProductVariant> productVariants) {
        return productVariants.stream().map(this::mapProductVariantDto).toList();
    }

    private ProductVariantDto mapProductVariantDto(ProductVariant productVariant) {
        return ProductVariantDto.builder()
                .color(productVariant.getColor())
                .id(productVariant.getId())
                .size(productVariant.getSize())
                .stockQuantity(productVariant.getStockQuantity())
                .build();
    }

    public List<ProductResourceDto> mapProductResourcesListDto(List<Resources> resources) {
        return resources.stream().map(this::mapResourceToDto).toList();
    }

    private ProductResourceDto mapResourceToDto(Resources resources) {
        return ProductResourceDto.builder()
                .id(resources.getId())
                .url(resources.getUrl())
                .name(resources.getName())
                .isPrimary(resources.getIsPrimary())
                .type(resources.getType())
                .build();
    }

    // *************************Order Mapping************************

    public List<OrderRequest> mapOrderDto(List<Order> orders) {
        return orders.stream()
                .map(this::mapOrderToDto)
                .toList();
    }

    private OrderRequest mapOrderToDto(Order order) {
        return OrderRequest.builder()
                .order_id(order.getOrder_id())
                .tracking_number(order.getTracking_number())
                .customer_name(order.getCustomer_name())
                .address(order.getAddress())
                .phone_number(order.getPhone_number())
                .district(order.getDistrict())
                .delivery_method(order.getDelivery_method() != null ? order.getDelivery_method().name() : null)
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .return_reason(order.getReturn_reason())
                .delivery_fee(order.getDelivery_fee())
                .created_at(order.getCreated_at())
                .updated_at(order.getUpdated_at())
                .orderItems(mapItemsToDto(order.getOrderItems()))
                .build();
    }

    private List<OrderItemRequest> mapItemsToDto(List<OrderItem> items) {
        return items.stream()
                .map(item -> OrderItemRequest.builder()
                        .item_id(item.getItem_id())
                        .order_id(item.getOrder().getOrder_id())
                        .product_id(item.getProduct().getProduct_id())
                        .origin_country(item.getOrigin_country())
                        .size(item.getSize())
                        .quantity(item.getQuantity())
                        .buying_price(item.getBuying_price())
                        .selling_price(item.getSelling_price())
                        .discount(item.getDiscount())
                        .buying_price_code(item.getBuying_price_code())
                        .promo_price(item.getPromo_price())
                        .final_price(item.getFinal_price())
                        .phone_number(item.getPhone_number())
                        .products(getProductDtos(List.of(item.getProduct())))
                        .build()
                )
                .collect(Collectors.toList());
    }
}