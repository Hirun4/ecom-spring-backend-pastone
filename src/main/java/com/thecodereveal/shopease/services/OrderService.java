package com.thecodereveal.shopease.services;

import com.stripe.model.PaymentIntent;
import com.thecodereveal.shopease.auth.dto.OrderResponse;
import com.thecodereveal.shopease.auth.entities.User;
import com.thecodereveal.shopease.dto.OrderDetails;
import com.thecodereveal.shopease.dto.OrderItemDetail;
import com.thecodereveal.shopease.dto.OrderItemRequest;
import com.thecodereveal.shopease.dto.OrderRequest;
import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.dto.ProductOrderCountDto;
import com.thecodereveal.shopease.entities.*;
import com.thecodereveal.shopease.mapper.ProductMapper;
import com.thecodereveal.shopease.repositories.OrderItemRepository;
import com.thecodereveal.shopease.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentIntentService paymentIntentService;

    @Autowired
    private ProductMapper productMapper;

    public List<OrderRequest> getAllOrders() {
    List<Order> orders = orderRepository.findAll();
    return productMapper.mapOrderDto(orders);
    }

    public List<ProductDto> getMostOrderedProducts() {
        List<Product> products = orderItemRepository.findMostOrderedProducts();
        return productMapper.getProductDtos(products);
    }


    public List<ProductOrderCountDto> getMostOrderedProductsCount() {
        List<ProductOrderCountDto> products = orderItemRepository.findProductOrderCounts();
        return products;
    }

    public List<OrderRequest> getOrdersByPhoneNumber(String phoneNumber) {
        List<Order> orders = orderRepository.findOrdersByPhoneNumber(phoneNumber);
        return productMapper.mapOrderDto(orders);
    }


// Helper to map OrderItem → OrderItemRequest
// private List<OrderItemRequest> getItemDetails(List<OrderItem> items) {
//     return items.stream()
//         .map(item -> OrderItemRequest.builder()
//             .item_id(item.getItem_id())
//             .order_id(item.getOrder().getOrder_id())
//             .product_id(item.getProduct_id())
//             .origin_country(item.getOrigin_country())
//             .size(item.getSize())
//             .quantity(item.getQuantity())
//             .buying_price(item.getBuying_price())
//             .selling_price(item.getSelling_price())
//             .discount(item.getDiscount())
//             .buying_price_code(item.getBuying_price_code())
//             .build()
//         )
//         .collect(Collectors.toList());
// }

    // private List<OrderItemRequest> getItemDetails(List<OrderItem> items) {
    //     return items.stream().map(item -> OrderItemRequest.builder()
    //     .item_id(item.getItem_id())
    //             .order_id(item.getOrder().getOrder_id())
    //             .product_id(item.getProduct_id())
    //             .origin_country(item.getOrigin_country())
    //             .size(item.getSize())
    //             .quantity(item.getQuantity())
    //             .buying_price(item.getBuying_price())
    //             .selling_price(item.getSelling_price())
    //             .discount(item.getDiscount())
    //             .buying_price_code(item.getBuying_price_code())
    //             .build()).toList();
    // }

    // @Transactional
    // public OrderResponse createOrder(OrderRequest orderRequest, Principal
    // principal) throws Exception {
    // User user = (User)
    // userDetailsService.loadUserByUsername(principal.getName());
    // Address address = user.getAddressList().stream().filter(address1 ->
    // orderRequest.getAddressId().equals(address1.getId())).findFirst().orElseThrow(BadRequestException::new);

    // Order order= Order.builder()
    // .user(user)
    // .address(address)
    // .totalAmount(orderRequest.getTotalAmount())
    // .orderDate(orderRequest.getOrderDate())
    // .discount(orderRequest.getDiscount())
    // .expectedDeliveryDate(orderRequest.getExpectedDeliveryDate())
    // .paymentMethod(orderRequest.getPaymentMethod())
    // .orderStatus(OrderStatus.PENDING)
    // .build();
    // List<OrderItem> orderItems =
    // orderRequest.getOrderItemRequests().stream().map(orderItemRequest -> {
    // try {
    // Product product=
    // productService.fetchProductById(orderItemRequest.getProductId());
    // OrderItem orderItem= OrderItem.builder()
    // .product(product)
    // .productVariantId(orderItemRequest.getProductVariantId())
    // .quantity(orderItemRequest.getQuantity())
    // .order(order)
    // .build();
    // return orderItem;
    // } catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // }).toList();

    // order.setOrderItemList(orderItems);
    // Payment payment=new Payment();
    // payment.setPaymentStatus(PaymentStatus.PENDING);
    // payment.setPaymentDate(new Date());
    // payment.setOrder(order);
    // payment.setAmount(order.getTotalAmount());
    // payment.setPaymentMethod(order.getPaymentMethod());
    // order.setPayment(payment);
    // Order savedOrder = orderRepository.save(order);

    // OrderResponse orderResponse = OrderResponse.builder()
    // .paymentMethod(orderRequest.getPaymentMethod())
    // .orderId(savedOrder.getId())
    // .build();
    // if(Objects.equals(orderRequest.getPaymentMethod(), "CARD")){
    // orderResponse.setCredentials(paymentIntentService.createPaymentIntent(order));
    // }

    // return orderResponse;

    // }

    // public Map<String,String> updateStatus(String paymentIntentId, String status)
    // {

    // try{
    // PaymentIntent paymentIntent= PaymentIntent.retrieve(paymentIntentId);
    // if (paymentIntent != null && paymentIntent.getStatus().equals("succeeded")) {
    // String orderId = paymentIntent.getMetadata().get("orderId") ;
    // Order order=
    // orderRepository.findById(UUID.fromString(orderId)).orElseThrow(BadRequestException::new);
    // Payment payment = order.getPayment();
    // payment.setPaymentStatus(PaymentStatus.COMPLETED);
    // payment.setPaymentMethod(paymentIntent.getPaymentMethod());
    // order.setPaymentMethod(paymentIntent.getPaymentMethod());
    // order.setOrderStatus(OrderStatus.IN_PROGRESS);
    // order.setPayment(payment);
    // Order savedOrder = orderRepository.save(order);
    // Map<String,String> map = new HashMap<>();
    // map.put("orderId", String.valueOf(savedOrder.getId()));
    // return map;
    // }
    // else{
    // throw new IllegalArgumentException("PaymentIntent not found or missing
    // metadata");
    // }
    // }
    // catch (Exception e){
    // throw new IllegalArgumentException("PaymentIntent not found or missing
    // metadata");
    // }
    // }

    // public List<OrderDetails> getOrdersByUser(String name) {
    //     User user = (User) userDetailsService.loadUserByUsername(name);
    //     List<Order> orders = orderRepository.findByUser(user);
    //     return orders.stream().map(order -> {
    //         return OrderDetails.builder()
    //                 .id(order.getId())
    //                 .orderDate(order.getOrderDate())
    //                 .orderStatus(order.getOrderStatus())
    //                 .shipmentNumber(order.getShipmentTrackingNumber())
    //                 .address(order.getAddress())
    //                 .totalAmount(order.getTotalAmount())
    //                 .orderItemList(getItemDetails(order.getOrderItemList()))
    //                 .expectedDeliveryDate(order.getExpectedDeliveryDate())
    //                 .build();
    //     }).toList();

    // }

    // private List<OrderItemDetail> getItemDetails(List<OrderItem> orderItemList) {

    // return orderItemList.stream().map(orderItem -> {
    // return OrderItemDetail.builder()
    // .id(orderItem.getId())
    // .itemPrice(orderItem.getItemPrice())
    // .product(orderItem.getProduct())
    // .productVariantId(orderItem.getProductVariantId())
    // .quantity(orderItem.getQuantity())
    // .build();
    // }).toList();
    // }

    // public void cancelOrder(UUID id, Principal principal) {
    // User user = (User)
    // userDetailsService.loadUserByUsername(principal.getName());
    // Order order = orderRepository.findById(id).get();
    // if(null != order && order.getUser().getId().equals(user.getId())){
    // order.setOrderStatus(OrderStatus.CANCELLED);
    // //logic to refund amount
    // orderRepository.save(order);
    // }
    // else{
    // new RuntimeException("Invalid request");
    // }

    // }
}
