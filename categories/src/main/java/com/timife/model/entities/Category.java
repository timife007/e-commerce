package com.timife.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    private String name;

    @ManyToOne
    @JoinColumn(name = "genderId")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @JsonBackReference
    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL)
    private List<Category> subCategories = new ArrayList<>();

}
