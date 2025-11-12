package com.canse.product.controllers;

import com.canse.product.entities.RegistrationRequest;
import com.canse.product.entities.User;
import com.canse.product.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest registrationRequest){
        return userService.registerUser(registrationRequest);
    }

    @GetMapping("verify-email/{token}")
    public User verifyEmail(@PathVariable String token){
        return userService.validateToken(token);
    }
}