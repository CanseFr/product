package com.canse.product.services;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import com.canse.product.repos.ImageRepository;
import com.canse.product.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
//        Long oldProdImgId = this.getProductById(product.getId()).getImage().getId();
//        Long newProdImgId = product.getImage().getId();
        Product prodUpdate = productRepository.save(product);
//        if(!Objects.equals(oldProdImgId, newProdImgId)){
//            imageRepository.deleteById(oldProdImgId);
//        }
        return prodUpdate;
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
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
