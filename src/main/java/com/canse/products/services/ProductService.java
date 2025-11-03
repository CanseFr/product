package com.canse.products.services;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    void deleteProductById(Long id);

    Product getProductById(Long id);

    List<Product> getAllProducts();

//    --

    List<Product> findByNameProduct(String nameProduct);

    List<Product> findByNameProductContains(String part);

    List<Product> findByNameAndPrice(String name, Double price);

    List<Product> findByCategory(Category category);
    List<Product> findByCategoryId(Long id);

    List<Product> findByOrderByNameProductAsc();

    List<Product> orderProductNameAndPrice();


}
