package com.canse.product.services;

import com.canse.product.dto.ProductDto;
import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    //    Product saveProduct(Product product);
    //    Product getProductById(Long id);
    //    List<Product> getAllProducts();
//    Product updateProduct(Product product);
    ProductDto saveProduct(ProductDto product);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();

    ProductDto updateProduct(ProductDto product);
    void deleteProduct(Product product);
    void deleteProductById(Long id);

//    --

    List<Product> findByNameProduct(String nameProduct);
    List<Product> findByNameProductContains(String part);
    List<Product> findByNameAndPrice(String name, Double price);
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryId(Long id);
    List<Product> findByOrderByNameProductAsc();
    List<Product> orderProductNameAndPrice();

// --

    ProductDto convertEntityDto(Product product);
    Product convertEntity(ProductDto productDto);
}
