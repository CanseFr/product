package com.canse.product.controllers;

import com.canse.product.entities.User;
import com.canse.product.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
public class AuthRestController {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody User user) {
        return myUserDetailsService.loadUserByUsername(user.getUsername());
    }


}
