package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(targetEntity = Gender.class)
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Long genderId;

    @OneToMany(targetEntity = Section.class,cascade = CascadeType.ALL, mappedBy = "categoryId")
    private List<Long> sectionIds;
}
