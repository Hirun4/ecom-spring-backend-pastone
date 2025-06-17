package com.thecodereveal.shopease.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer_order_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerName;
    private String address;
    private String phoneNumber;
    private String district;
    private BigDecimal deliveryFee;
    private String deliveryMethod; // e.g., STANDARD, EXPRESS
    private String paymentSlipUrl; // URL to uploaded payment slip
    private Date createdAt;

    @OneToMany(mappedBy = "customerOrderRequest", cascade = CascadeType.ALL)
    private List<CustomerOrderRequestItem> items;

    private String status;

    private String paymentMethod;// e.g., PENDING, APPROVED, REJECTED
}