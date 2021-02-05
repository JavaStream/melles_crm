package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepositorie extends JpaRepository<Color, Long> {
    List<Color> findByCategory(Category category);

    //void deleteByCategoryAndId(Category category, Long id);
    void deleteByCategoryIdAndId(Long categoryId, Long id);
}
