package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gender_category")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;

    @OneToMany(targetEntity = Category.class, cascade = CascadeType.ALL, mappedBy = "gender")
    List<Category> categories;
}
