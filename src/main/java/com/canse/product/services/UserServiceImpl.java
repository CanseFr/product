package com.canse.product.services;

import com.canse.product.entities.Role;
import com.canse.product.entities.User;
import com.canse.product.repos.RoleRepository;
import com.canse.product.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Role addRole(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public User addRoleToUser(String username, String roleName) {
        User user =  this.userRepository.findByUsername(username);
        Role role  =   this.roleRepository.findByName(roleName);
        user.getRoles().add(role);
        return user;
    }
}
