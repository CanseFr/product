package com.canse.product;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import com.canse.product.services.UserService;
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

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);
    }

//    @PostConstruct
//    void init() {
//        userService.addRole(new Role(null,"ADMIN"));
//        userService.addRole(new Role(null,"User"));
//
//        userService.saveUser(new User(null, "admin","admin",true, null));
//        userService.saveUser(new User(null, "canse","admin",true, null));
//
//        userService.addRoleToUser("admin", "ADMIN");
//        userService.addRoleToUser("canse", "USER");
//    }


}

