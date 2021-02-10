package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositorie extends CrudRepository<Order, Long> {
    List<Order> findAll();
}
