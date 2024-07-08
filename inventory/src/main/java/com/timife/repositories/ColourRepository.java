package com.timife.repositories;

import com.timife.model.entities.Colour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColourRepository extends JpaRepository<Colour, Long> {
}
