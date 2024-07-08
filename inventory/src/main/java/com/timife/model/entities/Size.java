package com.timife.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Table(name = "size")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    private String size;
}
