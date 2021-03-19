package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();
    List<Order> findByStatus(OrderStatus status);

    List<Order> findByStatusNot(OrderStatus orderStatus);
}
