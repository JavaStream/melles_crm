package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.OrderStatus;
import com.javastream.melles_crm.service.SettingService;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  Статус заказа "Завершено" определяется как последний порядковый номер созданных статусов.
 */


@Controller
@RequestMapping("/settings")
public class SettingsController {

    private SettingService settingService;

    public SettingsController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping
    public String allSettings(Model model) {
        return "settings/settings";
    }

    @GetMapping("/orders")
    public String orderStatus(Model model) {
        List<OrderStatus> orderStatuses = settingService.findOrderStatuses();

        model.addAttribute("statuses", orderStatuses);
        model.addAttribute("newStatus", new OrderStatus());
        return "settings/order-settings";
    }

    @PostMapping("/orders/add")
    public String addOrderStatus(@ModelAttribute("orderStatus") OrderStatus orderStatus) {
        int lastNumber = settingService.getLastOrderNumber();

        orderStatus.setNumber(++lastNumber);
        settingService.saveOrderStatus(orderStatus);

        return "redirect:/settings/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrderStatusForm(@PathVariable("id") String id, Model model) {
        OrderStatus status = settingService.findById(id);

        model.addAttribute("status", status);

        return "settings/order-settings-edit";
    }

    @PostMapping("/orders/edit/{id}")
    public String updateOrderStatus(@ModelAttribute("orderStatus") OrderStatus orderStatus) {
        settingService.saveOrderStatus(orderStatus);

        return "redirect:/settings/orders";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrderStatus(@PathVariable("id") String id) {
        OrderStatus status = settingService.findById(id);

        settingService.deleteOrderStatus(status);

        return "redirect:/settings/orders";
    }
}