package com.canse.product.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameProduct;
    private Double priceProduct;
    private Date dateCreated;

    @ManyToOne()
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Image> images;
}
