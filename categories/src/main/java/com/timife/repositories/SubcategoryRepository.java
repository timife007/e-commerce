package com.timife.repositories;

import com.timife.model.entities.Category;
import com.timife.model.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query(
            value = "SELECT s FROM Subcategory s WHERE s.name = :name AND s.category.id = :categoryId")
    Subcategory findByNameAndCategoryId(@Param("name") String name,@Param("categoryId") Long categoryId);
}
