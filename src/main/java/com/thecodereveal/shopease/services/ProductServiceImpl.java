package com.thecodereveal.shopease.services;

import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.dto.ProductStockDto;
import com.thecodereveal.shopease.entities.*;
import com.thecodereveal.shopease.exceptions.ResourceNotFoundEx;
import com.thecodereveal.shopease.mapper.ProductMapper;
import com.thecodereveal.shopease.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // @Autowired
    // private ProductStockRepository productStockRepository;


    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<ProductDto> getAllProducts() {

        List<Product> products = productRepository.findAll();
        return productMapper.getProductDtos(products);

    }

    @Override
    public ProductDto getProductById(Integer id) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Product Not Found!"));

        ProductDto productDto = new ProductDto();
        productDto.setProduct_id(product.getProduct_id());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setImage_url(product.getImage_url());
        productDto.setOrigin_country(product.getOrigin_country());
        productDto.setStock_quantity(product.getStock_quantity());
        productDto.setStock_status(product.getStock_status());
        productDto.setBuying_price_code(product.getBuying_price_code());
        productDto.setCreated_at(product.getCreated_at());
        productDto.setUpdated_at(product.getUpdated_at());

        // Map product stock to DTOs
        List<ProductStockDto> stockDtos = product.getStocks().stream().map(stock -> ProductStockDto.builder()
                .stock_id(stock.getStock_id())
                .product_id(product.getProduct_id())
                .size(stock.getSize())
                .quantity(stock.getQuantity())
                .build())
                .toList();

        productDto.setStocks(stockDtos);
        return productDto;
    }

    @Override
    public List<ProductDto> getProductByCategory(String category) throws Exception {
        List<Product> products = productRepository.findByCategory(category);
        if(products.isEmpty()){
            throw new ResourceNotFoundEx("Product Not Found!");
        }
        return productMapper.getProductDtos(products);

    }


}
