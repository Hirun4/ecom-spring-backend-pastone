package com.thecodereveal.shopease.repositories;

import com.thecodereveal.shopease.entities.CustomerOrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRequestRepository extends JpaRepository<CustomerOrderRequest, Integer> {}
