package com.timife.repositories;

import com.timife.model.entities.Section;
import com.timife.model.entities.SectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionItemRepository extends JpaRepository<SectionItem, Long> {

    @Query(
            value = "SELECT * FROM section_item u WHERE u.section_id = ?1",
            nativeQuery = true)
    Section findSectionItemBySection(Long sectionId);
}
