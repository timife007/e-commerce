package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product_colour")
public class Colour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String colour;
}
