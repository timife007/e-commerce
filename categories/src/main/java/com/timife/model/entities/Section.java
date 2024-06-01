package com.timife.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "section")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sectionName;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Long categoryId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sectionId")
    private List<Long> sectionItemIds;
}
