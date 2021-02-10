package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepositorie extends CrudRepository<OrderStatus, Long> {
    List<OrderStatus> findAll();
}
