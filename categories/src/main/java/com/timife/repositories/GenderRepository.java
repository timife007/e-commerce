package com.timife.repositories;

import com.timife.model.GenderCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<GenderCategory, Long> {

    GenderCategory findByName(String name);
}
