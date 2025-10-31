package com.canse.product;

import com.canse.product.entities.Product;
import com.canse.product.repos.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ProductApplicationTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct() {
        Product product = new Product("Pc Dell", 2250.00, new Date());
        productRepository.save(product);
    }

    @Test
    public void testFindProductById() {
        Product product = productRepository.findById(1L).get();
        System.out.println(product);
    }

    @Test
    public void testUpdateProduct() {
        Product product = productRepository.findById(1L).get();
        product.setPriceProduct(2000.0);
        productRepository.save(product);
        System.out.println(product);
    }

    @Test
    public void testDeleteProduct() {
        productRepository.deleteById(1L);
    }

    @Test
    public void testFindAllProducts() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            System.out.println(product);
        }
    }

}
