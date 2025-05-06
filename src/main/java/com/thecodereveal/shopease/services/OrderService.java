package com.thecodereveal.shopease.services;

import com.thecodereveal.shopease.auth.dto.OrderResponse;
import com.thecodereveal.shopease.auth.entities.User;
import com.thecodereveal.shopease.dto.OrderDetails;
import com.thecodereveal.shopease.dto.OrderItemDetail;
import com.thecodereveal.shopease.dto.OrderRequest;
import com.thecodereveal.shopease.entities.*;
import com.thecodereveal.shopease.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, Principal principal) throws Exception {
        logger.info("Creating order for user: " + principal.getName());
        logger.info("Order request: " + orderRequest);
        Order order = Order.builder()
                .order_id(orderRequest.getOrder_id())
//                .street(orderRequest.getStreet())
                // Ensure this is an int
                .created_at(orderRequest.getCreated_at())

                .status(orderRequest.getStatus())
                .build();

        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream().map(orderItemRequest -> {
            try {
                OrderItem orderItem = OrderItem.builder()
                        .quantity(orderItemRequest.getQuantity())
                        .size(orderItemRequest.getSize())
                        .final_price(orderItemRequest.getFinal_price())
                        .order(order)
                        .build();
                logger.info("Order item created: " + orderItem);
                return orderItem;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();

        order.setOrderItemList(orderItems);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(savedOrder.getOrder_id())
                .build();
    }

    public List<OrderDetails> getOrdersByUser(String email) {
        logger.info("Fetching orders for user with email: " + email);

        // Fetch the user by email
        User user = (User) userDetailsService.loadUserByUsername(email);
        String phoneNumber = user.getPhoneNumber();

        logger.info("Fetching orders for user with phone number: " + phoneNumber);

        // Fetch orders using the phone number
        List<Order> orders = orderRepository.findByUserPhoneNumber(phoneNumber);

        logger.info("Orders fetched: " + orders);
        logger.info("Found {} orders for user", orders.size());

        return orders.stream().map(order -> OrderDetails.builder()
                .order_id(order.getOrder_id())
                .created_at(order.getCreated_at())
                .status(order.getStatus())
//                .street(order.getStreet().getStreet())
                .orderItemList(order.getOrderItemList().stream().map(orderItem -> OrderItemDetail.builder()
                        .final_price(orderItem.getFinal_price())
                        .size(orderItem.getSize())
                        .quantity(orderItem.getQuantity())
                        .build()).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    public void cancelOrder(UUID id, Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getUser().getPhoneNumber().equals(user.getPhoneNumber())) {
            order.setStatus("CANCELLED");
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Unauthorized action");
        }
    }
}
