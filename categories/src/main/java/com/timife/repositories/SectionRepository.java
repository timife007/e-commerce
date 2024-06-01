package com.timife.repositories;

import com.timife.model.entities.Category;
import com.timife.model.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(
            value = "SELECT * FROM section u WHERE u.name = ?1 AND u.category_id = ?2",
            nativeQuery = true)
    Section findSectionByNameAndCategory(String name, Long categoryId);
}
