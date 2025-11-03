package com.canse.products.services;

import com.canse.product.entities.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
    void deleteCategoryById(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();


}
