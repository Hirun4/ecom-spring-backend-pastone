package com.thecodereveal.shopease.auth.dto;

import com.thecodereveal.shopease.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {

    private String token;
    private User user;
}
