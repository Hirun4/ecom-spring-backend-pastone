package com.thecodereveal.shopease.repositories;

import com.thecodereveal.shopease.entities.RefundRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefundRequestRepository extends JpaRepository<RefundRequest, Integer> {
    Optional<RefundRequest> findByOrderId(Long orderId);
}