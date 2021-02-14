package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.model.OrderStatus;
import com.javastream.melles_crm.model.ProductInOrder;
import com.javastream.melles_crm.repo.OrderRepositorie;
import com.javastream.melles_crm.repo.ProductInOrderRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepositorie orderRepositorie;
    private ProductInOrderRepositorie productInOrderRepositorie;


    public OrderService(OrderRepositorie orderRepositorie, ProductInOrderRepositorie productInOrderRepositorie) {
        this.orderRepositorie = orderRepositorie;
        this.productInOrderRepositorie = productInOrderRepositorie;
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

    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepositorie.findByStatus(orderStatus);

        return orders;
    }

    public List<ProductInOrder> findProductsInOrder(Order order) {
        List<ProductInOrder> products = productInOrderRepositorie.findByOrder(order);
        return products;
    }

    public void saveProductInOrder(ProductInOrder productInOrder) {
        productInOrderRepositorie.save(productInOrder);
    }

    public void deleteProductInOrder(String productInOrderId) {
        long id = Long.parseLong(productInOrderId);
        productInOrderRepositorie.deleteById(id);
    }

    public List<Order> findActiveOrders(OrderStatus orderStatus) {
        List<Order> orders = orderRepositorie.findByStatusNot(orderStatus);

        return orders;
    }

}
