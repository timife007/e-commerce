package com.timife.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sub_category")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category")
    private Category category;
}
