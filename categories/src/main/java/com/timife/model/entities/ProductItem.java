package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product_item")
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double salePrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne
    @JoinColumn(name = "product_colour_id")
    private ProductColour colour;
    private String productCode;

    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private ProductSize size;
    private String originalPrice;
    private Integer qty_in_stock;
}
