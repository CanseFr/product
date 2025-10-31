package com.canse.product;

import com.canse.product.entities.Product;
import com.canse.product.repos.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ProductApplicationTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct(){
        Product product = new Product("Pc Dell",2250.00, new Date());
        productRepository.save(product);
    }
}
