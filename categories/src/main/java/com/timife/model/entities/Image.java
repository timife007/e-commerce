package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "product_image")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String imageUrl;
    private String type;

    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;
}
