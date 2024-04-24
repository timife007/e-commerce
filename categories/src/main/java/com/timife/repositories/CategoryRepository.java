package com.timife.repositories;

import com.timife.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "SELECT * FROM category u WHERE u.name = ?1 AND u.gender.id = ?2",
            nativeQuery = true)
    Category findByNameAndGender(String name, Long gender);
}
