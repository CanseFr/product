package com.canse.product.dto;

import com.canse.product.entities.Category;
import com.canse.product.entities.Image;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String nameProduct;
    private Double priceProduct;
    private Date dateCreated;
    private Category category;
    private List<Image> images;
}
