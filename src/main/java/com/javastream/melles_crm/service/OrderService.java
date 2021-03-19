package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.model.OrderStatus;
import com.javastream.melles_crm.model.ProductInOrder;
import com.javastream.melles_crm.repo.OrderRepository;
import com.javastream.melles_crm.repo.ProductInOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductInOrderRepository productInOrderRepository;


    public OrderService(OrderRepository orderRepository, ProductInOrderRepository productInOrderRepository) {
        this.orderRepository = orderRepository;
        this.productInOrderRepository = productInOrderRepository;
    }

    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<Order> sortedOrders = orders.stream().sorted(Comparator.comparing(Order::getDate).thenComparing(Order::getNumber)).collect(Collectors.toList());
        return sortedOrders;
    }

    public Order findById(String id) {
        return orderRepository.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void deleteById(String id) {
        long orderId = Long.parseLong(id);
        orderRepository.deleteById(orderId);
    }

    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByStatus(orderStatus);

        return orders;
    }

    public List<ProductInOrder> findProductsInOrder(Order order) {
        return productInOrderRepository.findByOrder(order);
    }

    public void saveProductInOrder(ProductInOrder productInOrder) {
        productInOrderRepository.save(productInOrder);
    }

    public void deleteProductInOrder(String productInOrderId) {
        long id = Long.parseLong(productInOrderId);
        productInOrderRepository.deleteById(id);
    }

    public List<Order> findActiveOrders(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByStatusNot(orderStatus);

        return orders;
    }

}
