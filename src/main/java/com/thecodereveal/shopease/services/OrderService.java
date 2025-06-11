package com.thecodereveal.shopease.services;

import com.thecodereveal.shopease.dto.OrderRequest;
import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.dto.ProductOrderCountDto;
import com.thecodereveal.shopease.entities.*;
import com.thecodereveal.shopease.mapper.ProductMapper;
import com.thecodereveal.shopease.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductCodesRepository productCodesRepository;

    @Autowired
    private ProductStockRepository productStockRepository;



    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentIntentService paymentIntentService;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CustomerOrderRequestRepository customerOrderRequestRepository;

    public List<OrderRequest> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return productMapper.mapOrderDto(orders);
    }

    public List<ProductDto> getMostOrderedProducts() {
        List<Product> products = orderItemRepository.findMostOrderedProducts();
        return productMapper.getProductDtos(products);
    }

    public List<ProductOrderCountDto> getMostOrderedProductsCount() {
        return orderItemRepository.findProductOrderCounts();
    }

    public List<OrderRequest> getOrdersByPhoneNumber(String phoneNumber) {
        List<Order> orders = orderRepository.findOrdersByPhoneNumber(phoneNumber);
        return productMapper.mapOrderDto(orders);
    }

    public void placeOrder(OrderRequest orderRequest, String paymentMethod, String paymentSlipUrl) {
        if ("BANK_TRANSFER".equalsIgnoreCase(paymentMethod)) {
            CustomerOrderRequest cor = CustomerOrderRequest.builder()
                    .customerName(orderRequest.getCustomer_name())
                    .address(orderRequest.getAddress())
                    .phoneNumber(orderRequest.getPhone_number())
                    .deliveryMethod("Courier")
                    .district(orderRequest.getDistrict())
                    .deliveryFee(orderRequest.getDelivery_fee())
                    .paymentSlipUrl(paymentSlipUrl)
                    .createdAt(new Date())
                    .status("PENDING")
                    .build();
            cor = customerOrderRequestRepository.save(cor);

            final CustomerOrderRequest corFinal = cor;
            List<CustomerOrderRequestItem> items = orderRequest.getOrderItems().stream().map(item -> {
                try {
                    Product product = productService.getProductEntityById(item.getProduct_id());
                    String buyingPriceCode = product.getBuying_price_code();
                    Product_codes productCode = productCodesRepository.findByCode(buyingPriceCode);
                    return CustomerOrderRequestItem.builder()
                            .customerOrderRequest(corFinal)
                            .product(product)
                            .size(item.getSize())
                            .quantity(item.getQuantity())
                            .origin_country(item.getOrigin_country())
                            .buying_price_code(buyingPriceCode)
                            .buying_price(productCode != null ? productCode.getBuyingPrice() : null)
                            .selling_price(item.getSelling_price())
                            .promo_price(item.getPromo_price())
                            .final_price(item.getFinal_price())
                            .phone_number(item.getPhone_number())
                            .discount(item.getDiscount())

                            .build();
                } catch (Exception e) {
                    throw new RuntimeException("Product not found for id: " + item.getProduct_id());
                }
            }).collect(Collectors.toList());

            cor.setItems(items);
            customerOrderRequestRepository.save(cor);
        } else if ("CASH_ON_DELIVERY".equalsIgnoreCase(paymentMethod)) {
            Order order = Order.builder()
                    .customer_name(orderRequest.getCustomer_name())
                    .address(orderRequest.getAddress())
                    .phone_number(orderRequest.getPhone_number())
                    .district(orderRequest.getDistrict())
                    .delivery_method(DeliveryMethod.Courier)
                    .delivery_fee(orderRequest.getDelivery_fee())
                    .created_at(new Date())
                    .status(OrderStatus.PENDING)
                    .build();
            order = orderRepository.save(order);

            final Order orderFinal = order;
            List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(item -> {
                try {
                    Product product = productService.getProductEntityById(item.getProduct_id());
                    String buyingPriceCode = product.getBuying_price_code();
                    Product_codes productCode = productCodesRepository.findByCode(buyingPriceCode);
                    return OrderItem.builder()
                            .order(orderFinal)
                            .product(product)
                            .size(item.getSize())
                            .quantity(item.getQuantity())
                            .buying_price_code(buyingPriceCode)
                            .buying_price(productCode != null ? productCode.getBuyingPrice() : null)
                            .selling_price(item.getSelling_price())
                            .promo_price(item.getPromo_price())
                            .final_price(item.getFinal_price())
                            .phone_number(item.getPhone_number())
                            .discount(item.getDiscount())
                            .origin_country(item.getOrigin_country())
                            .build();
                } catch (Exception e) {
                    throw new RuntimeException("Product not found for id: " + item.getProduct_id());
                }
            }).collect(Collectors.toList());

            order.setOrderItems(orderItems);
            orderRepository.save(order);
        }
    }

    public void approveBankTransferOrder(Integer requestId) {
        CustomerOrderRequest cor = customerOrderRequestRepository.findById(requestId).orElseThrow();
        // Implement approval logic here
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            String size = item.getSize();
            int qty = item.getQuantity();

            Product_stock stock = product.getStocks().stream()
                    .filter(s -> s.getSize().equals(size))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Stock not found for product " + product.getProduct_id() + " size " + size));
            stock.setQuantity(stock.getQuantity() + qty);
            productStockRepository.save(stock);
        }
    }

    // OrderService.java
    @Transactional
    public void clearPromoPricesByPhone(String phoneNumber) {
        List<Order> orders = orderRepository.findOrdersByPhoneNumber(phoneNumber);
        for (Order order : orders) {
            if (order.getOrderItems() != null) {
                for (OrderItem item : order.getOrderItems()) {
                    if (item.getPromo_price() > 0) {
                        item.setPromo_price(0);
                        orderItemRepository.save(item);
                    }
                }
            }
        }
    }
}