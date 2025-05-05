package com.thecodereveal.shopease.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thecodereveal.shopease.auth.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue
    private UUID order_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "final_price", nullable = false)
    private int final_price;

    @Column(name = "status", nullable = false)
    private String status;

    // Relationship with Address using phone_number
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "phone_number", referencedColumnName = "phoneNumber", nullable = false)
//    private Address street;

    // Relationship with User using phone_number (but not insertable/updatable)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phone_number",referencedColumnName = "phone_number", insertable = false, updatable = false)
    @JsonIgnore
    private User user;



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;
}
