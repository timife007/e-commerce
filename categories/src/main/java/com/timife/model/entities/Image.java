package com.timife.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "image")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    private String imageUrl;
    private String type;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product = null;
}
