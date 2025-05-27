package com.thecodereveal.shopease.services;

import java.util.List;
import java.util.UUID;

import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.entities.Product;


public interface ProductService {

    // public Product addProduct(ProductDto product);
    public List<ProductDto> getAllProducts();

    public List<ProductDto> getProductByCategory(String category) throws Exception;
    
    ProductDto getProductById(Integer id)throws Exception;

    // Product updateProduct(ProductDto productDto, UUID id);

    // Product fetchProductById(UUID uuid) throws Exception;


}
