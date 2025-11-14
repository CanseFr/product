package com.canse.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;

    @Column(name = "IMAGE", length = 4048576)
    @Lob
    private byte[] image;

    @ManyToOne()
    @JoinColumn(name = "PRODUCT_ID")
    @JsonIgnore
    Product  product;

}
