package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.*;
import com.javastream.melles_crm.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private SettingService settingService;
    private CategoryService categoryService;
    private ProductService productService;
    private ProductInOrderService productInOrderService;

    public OrderController(OrderService orderService, UserService userService, SettingService settingService,
                           CategoryService categoryService, ProductService productService, ProductInOrderService productInOrderService) {
        this.orderService = orderService;
        this.userService = userService;
        this.settingService = settingService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.productInOrderService = productInOrderService;
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
        ORDER'S ENTRIES
     */

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable("orderId") String orderId, Model model) {
        Order order = orderService.findById(orderId);
        List<ProductInOrder> productsInOrder = orderService.findProductsInOrder(order);

        List<Category> categories = categoryService.findAllVisible();

        // Total
        BigDecimal total = productsInOrder.stream()
                .map(product -> BigDecimal.valueOf(product.getCount()).multiply(product.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("total", total);
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

        return "redirect:/orders/" + orderId;
    }

    @PostMapping("/{orderId}/add")
    public String addProductInOrder(@ModelAttribute("product") ProductInOrder productInOrder,
                                    @PathVariable("orderId") String orderId,
                                    @RequestParam String category,
                                    @RequestParam String color,
                                    @RequestParam String productId) {

        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);

        //check count, min count = 1
        if (productInOrder.getCount() == null) {
            productInOrder.setCount(1L);
        }

        // set default price, if price is null
        if (productInOrder.getPrice() == null) {
            productInOrder.setPrice(product.getPrice());
        }

        productInOrder.setOrder(order);
        productInOrder.setProduct(product);

        orderService.saveProductInOrder(productInOrder);

        return "redirect:/orders/" + orderId;
    }

    @GetMapping("/{orderId}/edit/{productId}")
    public String editProductInOrder(@PathVariable("orderId") String orderId,
                                     @PathVariable("productId") String productId, Model model) {

        List<Category> categories = categoryService.findAll();

        Order order = orderService.findById(orderId);
        List<OrderStatus> orderStatuses = settingService.findOrderStatuses();

        ProductInOrder productInOrder = productInOrderService.findById(productId);
        List<Color> colors = productInOrder.getProduct().getColor().getCategory().getColors();

        List<Product> products = productInOrder.getProduct().getColor().getProducts();
        products = products.stream().filter(Product::isVisible).collect(Collectors.toList());

        model.addAttribute("order", order);
        model.addAttribute("product", productInOrder);
        model.addAttribute("categories", categories);
        model.addAttribute("colors", colors);
        model.addAttribute("products", products);
        model.addAttribute("orderStatuses", orderStatuses);
        model.addAttribute("users", userService.findAll());

        return "orders/order/orderEdit";
    }

    @PostMapping("/{orderId}/product/{productId}/update")
    public String updateProductInOrder(@ModelAttribute("product") ProductInOrder productInOrder,
                                       @PathVariable("orderId") String orderId,
                                       @RequestParam("productId") String productId) {
        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);

        productInOrder.setProduct(product);
        productInOrder.setOrder(order);

        productInOrderService.save(productInOrder);

        return "redirect:/orders/" + orderId;
    }
}