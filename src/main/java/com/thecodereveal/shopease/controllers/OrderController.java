package com.thecodereveal.shopease.controllers;

import com.thecodereveal.shopease.dto.OrderDetails;
import com.thecodereveal.shopease.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user")
    public ResponseEntity<List<OrderDetails>> getOrdersByUser(Principal principal) {
        List<OrderDetails> orders = orderService.getOrdersByUser(principal.getName());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID id, Principal principal) {
        orderService.cancelOrder(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
