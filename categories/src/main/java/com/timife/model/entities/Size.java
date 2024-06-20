package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Table(name = "product_size")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String size;

    @OneToMany(mappedBy = "size")
    private Set<ProductItemSize> productItemSizeList;
}
