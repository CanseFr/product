package com.canse.product.services;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import com.canse.product.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

//    ---

    @Override
    public List<Product> findByNameProduct(String nameProduct) {
        return productRepository.findByNameProduct(nameProduct);
    }

    @Override
    public List<Product> findByNameProductContains(String part) {
        return productRepository.findByNameProductContains(part);
    }

    @Override
    public List<Product> findByNameAndPrice(String name, Double price) {
        return productRepository.findByNameAndPrice(name, price);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public List<Product> findByOrderByNameProductAsc() {
        return productRepository.findByOrderByNameProductAsc();
    }

    @Override
    public List<Product> orderProductNameAndPrice() {
        return productRepository.filterProductByNameAndPrice();
    }
}
