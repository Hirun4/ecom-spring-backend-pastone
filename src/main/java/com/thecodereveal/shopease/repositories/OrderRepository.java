package com.thecodereveal.shopease.repositories;

import com.thecodereveal.shopease.auth.entities.User;
import com.thecodereveal.shopease.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.phone_number = '0712237230'")
    List<Order> findOrdersByPhoneNumber();
}
