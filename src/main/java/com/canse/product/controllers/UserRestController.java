package com.canse.product.controllers;

import com.canse.product.entities.User;
import com.canse.product.repos.UserRepository;
import com.canse.product.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAllUsers();
    }
}
