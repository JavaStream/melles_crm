package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Order;
import com.javastream.melles_crm.model.OrderStatus;
import com.javastream.melles_crm.model.User;
import com.javastream.melles_crm.service.OrderService;
import com.javastream.melles_crm.service.ProductService;
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
    private ProductService productService;

    public OrderController(OrderService orderService, UserService userService,
                           SettingService settingService, ProductService productService) {

        this.orderService = orderService;
        this.userService = userService;
        this.settingService = settingService;
        this.productService = productService;
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
        Order order = orderService.findById(id);
        List<OrderStatus> orderStatuses = settingService.findOrderStatuses();

        model.addAttribute("order", order);
        model.addAttribute("orderStatuses", orderStatuses);
        model.addAttribute("users", userService.findAll());

        return "orders/orderEdit";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") Order order) {
       orderService.save(order);

        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") String id) {
        //       userService.deleteById(id);

        return "redirect:/clients";
    }


    /*
        ORDER ENTITY
     */

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable("id") String id, Model model) {
        Order order = orderService.findById(id);
        //productService.

        model.addAttribute("order", order);

        return "orders/order/order";
    }
}