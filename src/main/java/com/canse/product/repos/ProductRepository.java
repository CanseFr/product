package com.canse.product.repos;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameProduct(String nameProduct);
    List<Product> findByNameProductContains(String nameProduct);

    @Query("select p from Product p where p.nameProduct = ?1 and p.priceProduct = ?2")
//    @Query("select p from Product p where p.nameProduct = %:name and p.priceProduct = ?price")
    List<Product> findByNameAndPrice(String nameProduct, Double priceProduct);
//    List<Product> findByNameAndPrice(@Param("name"),@Param("price"));

    @Query("select p from Product p where p.category =?1 ")
    List<Product> findByCategory(Category category);

    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByOrderByNameProductAsc();
    List<Product> findByOrderByNameProductDesc();

    @Query("select p from Product p order by p.nameProduct ASC, p.priceProduct DESC")
    List<Product> filterProductByNameAndPrice();

//    ----

}
