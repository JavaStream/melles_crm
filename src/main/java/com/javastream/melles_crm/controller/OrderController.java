package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.model.OrderStatus;
import com.javastream.melles_crm.model.User;
import com.javastream.melles_crm.service.OrderService;
import com.javastream.melles_crm.service.SettingService;
import com.javastream.melles_crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private SettingService settingService;

    public OrderController(OrderService orderService, UserService userService, SettingService settingService) {
        this.orderService = orderService;
        this.userService = userService;
        this.settingService = settingService;
    }

    @GetMapping
    public String allOrders(Model model) {
        List<Order> orders = orderService.findAll();

        OrderStatus defultOrderStatus = settingService.getDefultOrderStatus();
        List<OrderStatus> orderStatuses = settingService.findOrderStatuses();

        Order order = new Order();
        order.setStatus(defultOrderStatus);

        model.addAttribute("orders", orders);
        model.addAttribute("newOrder", order);
        model.addAttribute("orderStatuses", orderStatuses);
        model.addAttribute("users", userService.findAll());

        return "orders/orders";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute("order") Order order, Model model) {
        Date date = order.getDate();
        Long id = order.getId();
        orderService.save(order);

        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable("id") String id, Model model) {
       /* User user = userService.findById(id);

        model.addAttribute("user", user);
*/
        return "clients/userEdit";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("user") User user) {
        /*     userService.save(user);*/

        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") String id) {
        //       userService.deleteById(id);

        return "redirect:/clients";
    }
}