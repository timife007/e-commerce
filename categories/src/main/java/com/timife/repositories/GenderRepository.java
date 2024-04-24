package com.timife.repositories;

import com.timife.model.entities.GenderCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<GenderCategory, Long> {

    GenderCategory findByName(String name);
}
