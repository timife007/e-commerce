package com.timife.repositories;

import com.timife.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "SELECT * FROM category u WHERE gender_id = ?1",
            nativeQuery = true)
    Category findCategoryByGender(Long genderId);
}
