package com.timife.repositories;

import com.timife.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByNameAndGenderId(String name, Long genderId);

    @Query(value = "SELECT u FROM Category u where u.gender.id = ?1")
    Optional<List<Category>> findByGenderId(Long genderId);
}
