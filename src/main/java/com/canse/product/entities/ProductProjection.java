package com.canse.product.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "nameProduct", types = Product.class)
public interface ProductProjection {
    public String getNameProduct();
}
