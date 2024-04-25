package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "sub_category")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private GenderCategory genderCategory;

    @OneToMany(mappedBy = "sub_category", cascade = CascadeType.ALL)
    Collection<ShopItem> shopItems;
}
