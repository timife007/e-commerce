package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "product_brand")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long brand_id;
    private String name;
    private String description;
}
