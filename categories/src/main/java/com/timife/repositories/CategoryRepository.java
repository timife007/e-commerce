package com.timife.repositories;

import com.timife.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "SELECT u FROM Category u WHERE u.name = ?1 AND u.gender_id = ?2 LIMIT 1",
            nativeQuery = true)
    Optional<Category> findCategoryByNameAndGender(String name, Long genderId);

    @Query(value = "SELECT u FROM Category u where u.gender.id = ?1")
    Optional<List<Category>> findByGenderId(Long genderId);
}
