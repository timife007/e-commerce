package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "brand")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    private String name;
    private String description;
}
