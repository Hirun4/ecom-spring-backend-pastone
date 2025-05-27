package com.thecodereveal.shopease.controllers;

import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.entities.Product;
import com.thecodereveal.shopease.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productList = productService.getAllProducts();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) throws Exception{
        ProductDto productDto = productService.getProductById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductByCatogory(@PathVariable String category) throws Exception{
        List<ProductDto> productList = productService.getProductByCategory(category);
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    // //   create Product
    // @PostMapping
    // public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto){
    //     Product product = productService.addProduct(productDto);
    //     return new ResponseEntity<>(product,HttpStatus.CREATED);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto,@PathVariable UUID id){
    //     Product product = productService.updateProduct(productDto,id);
    //     return new ResponseEntity<>(product,HttpStatus.OK);
    // }


}
