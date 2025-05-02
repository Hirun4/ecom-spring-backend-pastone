package com.thecodereveal.shopease.auth.controller;

import com.thecodereveal.shopease.auth.dto.UserDetailsDto;
import com.thecodereveal.shopease.auth.entities.User;
import com.thecodereveal.shopease.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserDetailController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> getUserProfile(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());

        if (null == user) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .addressList(user.getAddressList())
                .authorityList(user.getAuthorities().toArray()).build();

        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }

    @PutMapping("/profile/update/{id}")
    public ResponseEntity<UserDetailsDto> updateUserProfile(
            @PathVariable UUID id,
            @RequestBody UserDetailsDto userDetailsDto,
            Principal principal) {
        User user = userService.findById(id);

        if (null == user || !user.getEmail().equals(principal.getName())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Update user details
        user.setFirstName(userDetailsDto.getFirstName());
        user.setLastName(userDetailsDto.getLastName());
        // user.setEmail(userDetailsDto.getEmail());
        user.setPhoneNumber(userDetailsDto.getPhoneNumber());
        user.setAddressList(userDetailsDto.getAddressList());

        // Save updated user
        User updatedUser = userService.updateUser(user);

        UserDetailsDto updatedUserDetailsDto = UserDetailsDto.builder()
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                // .email(updatedUser.getEmail())
                .id(updatedUser.getId())
                .phoneNumber(updatedUser.getPhoneNumber())
                .addressList(updatedUser.getAddressList())
                .authorityList(updatedUser.getAuthorities().toArray()).build();

        return new ResponseEntity<>(updatedUserDetailsDto, HttpStatus.OK);
    }
}
