package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductInOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInOrderRepository extends CrudRepository<ProductInOrder, Long> {
    List<ProductInOrder> findByOrder(Order order);
    List<ProductInOrder> findByProduct(Product product);
}