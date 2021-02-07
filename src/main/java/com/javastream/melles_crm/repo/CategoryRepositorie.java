package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositorie extends CrudRepository<Category, Long> {
}
