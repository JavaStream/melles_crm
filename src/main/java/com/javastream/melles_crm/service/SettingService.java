package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.OrderStatus;
import com.javastream.melles_crm.model.User;
import com.javastream.melles_crm.repo.OrderStatusRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SettingService {

    private OrderStatusRepositorie orderStatusRepositorie;

    public SettingService(OrderStatusRepositorie orderStatusRepositorie) {
        this.orderStatusRepositorie = orderStatusRepositorie;
    }

    public List<OrderStatus> findOrderStatuses() {
        List<OrderStatus> statuses = orderStatusRepositorie.findAll();
        List<OrderStatus> sortedStatuses = statuses.stream().sorted(Comparator.comparing(OrderStatus::getNumber)).collect(Collectors.toList());
        return sortedStatuses;
    }

    public void saveOrderStatus(OrderStatus orderStatus) {
        orderStatusRepositorie.save(orderStatus);
    }

    public int getLastOrderNumber() {
        List<OrderStatus> statuses = orderStatusRepositorie.findAll();

        if (statuses.isEmpty()) return 0;

        OrderStatus orderStatus = statuses.stream().max(Comparator.comparing(OrderStatus::getNumber)).get();

        return orderStatus.getNumber();
    }

    public OrderStatus findById(String id) {
        return orderStatusRepositorie.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void deleteOrderStatus(OrderStatus status) {
        orderStatusRepositorie.delete(status);
    }

    public OrderStatus getDefultOrderStatus() {
        List<OrderStatus> statuses = findOrderStatuses();

        if (statuses.isEmpty()) return null;

        return statuses.get(0);
    }
}
