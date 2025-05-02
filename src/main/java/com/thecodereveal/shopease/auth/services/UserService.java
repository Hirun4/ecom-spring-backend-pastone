package com.thecodereveal.shopease.auth.services;

import com.thecodereveal.shopease.auth.entities.User;
import com.thecodereveal.shopease.auth.repositories.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    public User updateUser(User user) {
        return userDetailRepository.save(user);
    }

    public User findByEmail(String email) {
        return userDetailRepository.findByEmail(email);
    }

    public User findById(UUID id) {
        return userDetailRepository.findById(id).orElse(null);
    }
}
