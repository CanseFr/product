package com.canse.product.controllers;

import com.canse.product.entities.Category;
import com.canse.product.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@CrossOrigin
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Category getCategoriesById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

}
