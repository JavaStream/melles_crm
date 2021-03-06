package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductArrival;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductArrivalRepository extends CrudRepository<ProductArrival, Long> {
    List<ProductArrival> findByProduct(Product product);
}
