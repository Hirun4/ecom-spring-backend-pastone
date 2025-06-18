package com.thecodereveal.shopease.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "refund_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundRequest {
    @Id
    @GeneratedValue
    private int id;

    private Long orderId;

    private String reason;

    private String bankAccountNumber;

    private String bankBranch;

    private String bankName;

    private Double refundAmount;

    private String status; // e.g. PENDING, APPROVED, REJECTED

    private Date createdAt;

    @ElementCollection
    @CollectionTable(name = "refund_request_images", joinColumns = @JoinColumn(name = "refund_request_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;
}