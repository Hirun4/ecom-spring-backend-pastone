package com.thecodereveal.shopease.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer order_id;

    @Column(name = "tracking_number", length = 255)
    private String tracking_number;

    // @ManyToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @Column(name = "customer_id")
    // private int customer_id;

    @Column(name = "customer_name", length = 255)
    private String customer_name;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phone_number;

    @Column(name = "district", length = 100)
    private String district;

    @Enumerated(EnumType.STRING)     
    @Column(name = "delivery_method", length = 10 ,nullable = true)
    private DeliveryMethod delivery_method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private OrderStatus status;

    @Column(name = "return_reason", columnDefinition = "TEXT")
    private String return_reason;

    @Column(precision = 10, scale = 2)
    private BigDecimal delivery_fee;

    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "DATETIME")
    private java.util.Date created_at;


    @Column(name = "updated_at", insertable = false, columnDefinition = "DATETIME")
    private java.util.Date updated_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Column(name ="payment_method",length = 20)
    private String payment_method; // e.g., CASH_ON_DELIVERY, ONLINE_PAYMENT

}