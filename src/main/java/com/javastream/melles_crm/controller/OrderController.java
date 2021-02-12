package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.*;
import com.javastream.melles_crm.service.*;
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
    private CategoryService categoryService;
    private ProductService productService;

    public OrderController(OrderService orderService, UserService userService, SettingService settingService,
                           CategoryService categoryService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.settingService = settingService;
        this.categoryService = categoryService;
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
    public String addOrder(@ModelAttribute("order") Order order) {
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
       orderService.deleteById(id);

       return "redirect:/orders";
    }


    /*
        ORDER ENRIES
     */

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable("orderId") String orderId, Model model) {
        Order order = orderService.findById(orderId);
        List<ProductInOrder> productsInOrder = orderService.findProductsInOrder(order);

        List<Category> categories = categoryService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("categories", categories);
        model.addAttribute("products", productsInOrder);
        model.addAttribute("newProduct", new ProductInOrder());

        return "orders/order/order";
    }

    @GetMapping("/{orderId}/delete/{productId}")
    public String deleteProductInOrder(@PathVariable("orderId") String orderId,
                                       @PathVariable("productId") String productId) {

        orderService.deleteProductInOrder(productId);

        return "redirect:/orders/"+orderId;
    }

    @PostMapping("/{id}/add")
    public String addProductInOrder(@ModelAttribute("product") ProductInOrder productInOrder,
                                    @PathVariable("id") String orderId,
                                    @RequestParam String category,
                                    @RequestParam String color,
                                    @RequestParam String productId) {

        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);

        productInOrder.setOrder(order);
        productInOrder.setProduct(product);

        orderService.saveProductInOrder(productInOrder);

        return "redirect:/orders/"+orderId;
    }
}