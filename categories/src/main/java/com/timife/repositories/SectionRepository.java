package com.timife.repositories;

import com.timife.model.entities.Category;
import com.timife.model.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(
            value = "SELECT * FROM section u WHERE category_id = ?1",
            nativeQuery = true)
    Section findSectionByCategory(Long categoryId);
}
