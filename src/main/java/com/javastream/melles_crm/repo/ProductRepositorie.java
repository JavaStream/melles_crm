package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositorie extends JpaRepository<Product, Long> {
}
