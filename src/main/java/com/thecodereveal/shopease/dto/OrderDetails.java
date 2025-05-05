package com.thecodereveal.shopease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails {

    private UUID order_id;
    private Date created_at;
    private String status;
//    private String street; // Street from Address
    private List<OrderItemDetail> orderItemList;
}
