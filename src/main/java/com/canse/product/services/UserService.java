package com.canse.product.services;

import com.canse.product.entities.Role;
import com.canse.product.entities.User;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String roleName);
}
