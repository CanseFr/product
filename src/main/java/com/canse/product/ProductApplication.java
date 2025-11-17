package com.canse.product;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import com.canse.product.entities.Role;
import com.canse.product.entities.User;
import com.canse.product.services.UserService;
import com.canse.product.utils.EnvConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class ProductApplication implements CommandLineRunner {

    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    @Autowired
    UserService userService;

    @Autowired
    EnvConfig envConfig;

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);
    }

    @PostConstruct
    void init() {
         userService.addRole(new Role(null,"ADMIN"));
        userService.addRole(new Role(null,"USER"));


        User admin =  User.builder()
                .username(envConfig.getAdminUsername())
                .password(envConfig.getAdminPassword())
                .enabled(true)
                .build();
        User user =  User.builder()
                .username("canse")
                .password("canse")
                .enabled(true)
                .build();

        userService.saveUser(admin);
        userService.saveUser(user);

        userService.addRoleToUser(envConfig.getAdminUsername(), "ADMIN");
        userService.addRoleToUser("canse", "USER");
    }

}
