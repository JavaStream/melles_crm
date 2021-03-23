package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.model.OrderStatus;
import com.javastream.melles_crm.repo.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingService {

    private OrderStatusRepository orderStatusRepository;
    private OrderService orderService;

    public SettingService(OrderStatusRepository orderStatusRepository, OrderService orderService) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderService = orderService;
    }

    public List<OrderStatus> findOrderStatuses() {
        List<OrderStatus> statuses = orderStatusRepository.findAll();
        return statuses.stream()
                .sorted(Comparator.comparing(OrderStatus::getNumber)).collect(Collectors.toList());
    }

    public void saveOrderStatus(OrderStatus orderStatus) {
        orderStatusRepository.save(orderStatus);
    }

    public int getLastOrderNumber() {
        List<OrderStatus> statuses = orderStatusRepository.findAll();

        if (statuses.isEmpty()) return 0;

        OrderStatus orderStatus = statuses.stream().max(Comparator.comparing(OrderStatus::getNumber)).get();

        return orderStatus.getNumber();
    }

    public OrderStatus findById(String id) {
        return orderStatusRepository.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void deleteOrderStatus(OrderStatus status) {
        // Найти все заказы, в которых есть ссылка на статус и проставить null
        List<Order> orders = orderService.findByOrderStatus(status);

        for (Order order : orders) {
            order.setStatus(null);

            orderService.save(order);
        }

        orderStatusRepository.delete(status);
    }

    public OrderStatus getDefultOrderStatus() {
        List<OrderStatus> statuses = findOrderStatuses();

        if (statuses.isEmpty()) return null;

        return statuses.get(0);
    }
}
