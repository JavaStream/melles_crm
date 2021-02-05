package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositorie extends JpaRepository<Category, Long> {
}
