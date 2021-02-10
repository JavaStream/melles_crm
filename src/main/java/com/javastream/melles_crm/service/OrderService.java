package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.repo.OrderRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepositorie orderRepositorie;

    public OrderService(OrderRepositorie orderRepositorie) {
        this.orderRepositorie = orderRepositorie;
    }

    public List<Order> findAll() {
        List<Order> orders = orderRepositorie.findAll();
        List<Order> sortedOrders = orders.stream().sorted(Comparator.comparing(Order::getDate).thenComparing(Order::getNumber)).collect(Collectors.toList());
        return sortedOrders;
    }

    public Order findById(String id) {
        return orderRepositorie.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(Order order) {
        orderRepositorie.save(order);
    }

    public void deleteById(String id) {
        long orderId = Long.parseLong(id);
        orderRepositorie.deleteById(orderId);
    }

}
