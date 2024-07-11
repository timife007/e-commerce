package com.timife.repositories;

import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByNameAndGender(String name, Gender gender);

    Optional<List<Category>> findByGenderId(Long genderId);

    Optional<List<Category>> findByParentCategoryId(Long categoryId);
}
