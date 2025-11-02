package com.canse.product.repos;

import com.canse.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameProduct(String nameProduct);
    List<Product> findByNameProductContains(String nameProduct);

    @Query("select p from Product p where p.nameProduct = ?1 and p.priceProduct = ?2")
//    @Query("select p from Product p where p.nameProduct = %:nameProduct and p.priceProduct = ?priceProduct")
    List<Product> findByNameAndPrice(String nameProduct, Double priceProduct);
//    List<Product> findByNameAndPrice(@Param("nameProduct"),@Param("priceProduct"));

}
