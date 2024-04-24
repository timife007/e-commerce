package com.timife.repositories;

import com.timife.model.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("SELECT * FROM sub_category u WHERE u.name = ?1 AND u.genderCategory.id = ?1 AND u.category.id = ?2")
    SubCategory findSubCategoryByCategoryAndGender(String name, Long genderCategory, Long category);
}
