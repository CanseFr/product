package com.canse.product.controllers;

import com.canse.product.dto.ProductDto;
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
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        return productService.saveProduct(productDto);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDto updateProduct(@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
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
