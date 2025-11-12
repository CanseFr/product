package com.canse.product.controllers;

import com.canse.product.entities.Product;
import com.canse.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping()
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProductById(id);
    }

    @DeleteMapping("/prod-by-cat/{idCat}")
    public List<Product> getProductByCatId(@PathVariable("idCat") Long id){
        return productService.findByCategoryId(id);
    }

    @GetMapping("/prod-by-name/{name}")
    public List<Product> getProductByName(@PathVariable("name") String name){
        return productService.findByNameProduct(name);
    }

}
