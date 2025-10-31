package com.canse.products.services;

import com.canse.product.entities.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    void deleteProductById(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
}
