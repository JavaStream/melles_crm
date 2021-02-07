package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositorie extends CrudRepository<Product, Long> {
    List<Product> findByColor(Color color);
    void deleteByName(String name);
}
