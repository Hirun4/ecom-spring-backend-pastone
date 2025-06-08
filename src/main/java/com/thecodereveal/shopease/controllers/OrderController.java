package com.thecodereveal.shopease.controllers;

import com.thecodereveal.shopease.auth.dto.OrderResponse;
import com.thecodereveal.shopease.dto.OrderRequest;
import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.dto.ProductOrderCountDto;
import com.thecodereveal.shopease.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order1")
@CrossOrigin
public class OrderController {


    @Autowired
    OrderService orderService;

//     @PostMapping
//     public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, Principal principal) throws Exception {
//         OrderResponse orderResponse = orderService.createOrder(orderRequest,principal);
//             //return new ResponseEntity<>(order, HttpStatus.CREATED);
//
//         return new ResponseEntity<>(orderResponse,HttpStatus.OK);
//     }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer orderId) {
        try {
            orderService.cancelOrder(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // OrderController.java
    @PutMapping("/clear-promo/{phone}")
    public ResponseEntity<?> clearPromoPrices(@PathVariable String phone) {
        try {
            orderService.clearPromoPricesByPhone(phone);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(
            @RequestBody OrderRequest orderRequest,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String paymentSlipUrl) {
        try {
            orderService.placeOrder(orderRequest, paymentMethod, paymentSlipUrl);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping("/update-payment")
    // public ResponseEntity<?> updatePaymentStatus(@RequestBody Map<String,String> request){
    //     Map<String,String> response = orderService.updateStatus(request.get("paymentIntent"),request.get("status"));
    //     return new ResponseEntity<>(response,HttpStatus.OK);
    // }

    // @PostMapping("/cancel/{id}")
    // public ResponseEntity<?> cancelOrder(@PathVariable UUID id,Principal principal){
    //     orderService.cancelOrder(id,principal);
    //     return new ResponseEntity<>(HttpStatus.OK);
    // }

    @GetMapping
    public ResponseEntity<List<OrderRequest>> getAllOrder() {
        List<OrderRequest> orderList = orderService.getAllOrders();
        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/by-phone/{phone}")
    public ResponseEntity<List<OrderRequest>> getOrdersByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(orderService.getOrdersByPhoneNumber(phone));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productList= orderService.getMostOrderedProducts();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/products/count/{id}")
    public ResponseEntity<ProductOrderCountDto> getMostOrderedProductsCount(@PathVariable Integer id) {
        List<ProductOrderCountDto> productList= orderService.getMostOrderedProductsCount();

        
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Optional<ProductOrderCountDto> result = productList.stream()
        .filter(p -> p.getProductId().equals(id))
        .findFirst();
        return new ResponseEntity<>(result.orElse(null), HttpStatus.OK);
    }

}
