package com.canse.product.controllers;

import com.canse.product.entities.Category;
import com.canse.product.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat")
@CrossOrigin()
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping( "/{id}")
    public Category getCategoriesById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping()
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }


}
