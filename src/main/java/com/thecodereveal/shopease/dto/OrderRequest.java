package com.thecodereveal.shopease.dto;

import com.thecodereveal.shopease.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private UUID order_id;
    private Date created_at;
//    private Address street;
    private List<OrderItemRequest> orderItemRequests;


    private String status;

}
