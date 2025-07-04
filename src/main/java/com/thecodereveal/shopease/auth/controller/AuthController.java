package com.thecodereveal.shopease.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thecodereveal.shopease.auth.config.JWTTokenHelper;
import com.thecodereveal.shopease.auth.dto.LoginRequest;
import com.thecodereveal.shopease.auth.dto.RegistrationRequest;
import com.thecodereveal.shopease.auth.dto.RegistrationResponse;
import com.thecodereveal.shopease.auth.dto.UserToken;
import com.thecodereveal.shopease.auth.entities.User;
import com.thecodereveal.shopease.auth.services.RegistrationService;

@RestController
@CrossOrigin

@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTTokenHelper jwtTokenHelper;


    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUserName(), loginRequest.getPassword());

            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);

            if (authenticationResponse.isAuthenticated()) {
                User user = (User) authenticationResponse.getPrincipal();
                if (!user.isEnabled()) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                String token = jwtTokenHelper.generateToken(user.getEmail());
                UserToken userToken = UserToken.builder()
                        .token(token)
                        .user(user)
                        .build();
                return new ResponseEntity<>(userToken, HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
             // Log the exception for debugging
             System.out.println("Login failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request){
        RegistrationResponse registrationResponse = registrationService.createUser(request);

        return new ResponseEntity<>(registrationResponse,
                registrationResponse.getCode() == 200 ? HttpStatus.OK: HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String,String> map){
        String userName = map.get("userName");
        String code = map.get("code");

        User user= (User) userDetailsService.loadUserByUsername(userName);
        if(null != user && user.getVerificationCode().equals(code)){
            registrationService.verifyUser(userName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
