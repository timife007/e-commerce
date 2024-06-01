package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "section_item")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sectionItemName;

    @ManyToOne(targetEntity = Section.class)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Long sectionId;
}