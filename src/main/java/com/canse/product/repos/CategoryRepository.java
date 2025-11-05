package com.canse.product.repos;

import com.canse.product.entities.Category;
import com.canse.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path="cat")
public interface CategoryRepository extends JpaRepository<Category, Long> { }
