package com.canse.product.repos;

import com.canse.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameProduct(String nameProduct);
    List<Product> findByNameProductContains(String nameProduct);

}
