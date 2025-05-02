package com.thecodereveal.shopease.services;

import com.thecodereveal.shopease.auth.entities.User;
import com.thecodereveal.shopease.dto.AddressRequest;
import com.thecodereveal.shopease.entities.Address;
import com.thecodereveal.shopease.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(AddressRequest addressRequest, Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = Address.builder()
                .name(addressRequest.getName())
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .zipCode(addressRequest.getZipCode())
                .phoneNumber(addressRequest.getPhoneNumber())
                .user(user)
                .build();
        return addressRepository.save(address);
    }

    public Address updateAddress(UUID id, AddressRequest addressRequest, Principal principal) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            User user = (User) userDetailsService.loadUserByUsername(principal.getName());
            
            // Ensure the address belongs to the logged-in user
            if (!address.getUser().getId().equals(user.getId())) {
                throw new SecurityException("You are not authorized to update this address.");
            }

            address.setName(addressRequest.getName());
            address.setStreet(addressRequest.getStreet());
            address.setCity(addressRequest.getCity());
            address.setState(addressRequest.getState());
            address.setZipCode(addressRequest.getZipCode());
            address.setPhoneNumber(addressRequest.getPhoneNumber());
            
            return addressRepository.save(address);
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }
}
