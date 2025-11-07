package com.canse.product.controllers;

import com.canse.product.entities.Product;
import com.canse.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductRestController {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProductById(id);
    }

    @RequestMapping(value = "/prod-by-cat/{idCat}",method = RequestMethod.GET)
    public List<Product> getProductByCatId(@PathVariable("idCat") Long id){
        return productService.findByCategoryId(id);
    }

    @RequestMapping(value = "/prod-by-name/{name}",method = RequestMethod.GET)
    public List<Product> getProductByName(@PathVariable("name") String name){
        return productService.findByNameProduct(name);
    }

}
