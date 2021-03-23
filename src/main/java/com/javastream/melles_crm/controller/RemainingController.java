package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.*;
import com.javastream.melles_crm.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/remaining")
public class RemainingController {

    private ColorService colorService;
    private CategoryService categoryService;
    private ProductService productService;
    private ProductArrivalService productArrivalService;
    private OrderService orderService;
    private SettingService settingService;
    private ProductInOrderService productInOrderService;

    public RemainingController(ColorService colorService, CategoryService categoryService,
                               ProductService productService, OrderService orderService, SettingService settingService,
                               ProductArrivalService productArrivalService, ProductInOrderService productInOrderService) {
        this.colorService = colorService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderService = orderService;
        this.settingService = settingService;
        this.productArrivalService = productArrivalService;
        this.productInOrderService = productInOrderService;
    }

    @GetMapping
    public String selectCategories(Model model) {
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        return "remaining/categories";
    }

    @GetMapping("/category/{categoryId}")
    public String selectColors(@PathVariable("categoryId") String categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        List<Color> colors = colorService.findAllByCategory(category);

        model.addAttribute("colors", colors);
        model.addAttribute("category", category);
        return "remaining/colors";
    }

    @GetMapping("/category/{categoryId}/color/{colorId}")
    public String selectProducts(@PathVariable("categoryId") String categoryId,
                                 @PathVariable("colorId") String colorId,
                                 Model model) {
        Color color = colorService.findById(colorId);
        List<Product> products = productService.findAllByColor(color);

        // Считаем кол-во товаров: 1. во всех заказах, 2. В завершенных заказах, 3. в незавершенных заказах
        List<OrderStatus> stasuses = settingService.findOrderStatuses();
        OrderStatus finishStatus = stasuses.stream().max(Comparator.comparing(OrderStatus::getNumber)).get();

        List<Statistic> statistics = new ArrayList<>();

        for (Product product : products) {
            Statistic statistic = new Statistic();
            statistic.setProductId(product.getId());
            statistic.setProduct(product);

            // get incomming balance
            long incomingBalance = product.getIncomingBalance();
            statistic.setIncomingBalance(incomingBalance);

            // ger arrivals for this product
            List<ProductArrival> arrivals = productArrivalService.findAllByProduct(product);
            long arrivalCount = arrivals.stream().mapToLong(ProductArrival::getCount).sum();
            statistic.setArrival(arrivalCount);

            // get sold in total
            List<ProductInOrder> productsInOrders = productInOrderService.findAllByProduct(product);
            long soldCount = productsInOrders.stream().mapToLong(ProductInOrder::getCount).sum();

            // get reserve
            List<Order> activeOrders = orderService.findActiveOrders(finishStatus);
            long reserveCount = productsInOrders.stream()
                    .filter(prod -> { return activeOrders.contains(prod.getOrder()); })
                    .mapToLong(ProductInOrder::getCount).sum();
            statistic.setReserve(reserveCount);

            //get plannedBalance
            long plannedBalance = (incomingBalance + arrivalCount) - soldCount;
            statistic.setPlannedBalance(plannedBalance);

            statistics.add(statistic);
        }


        model.addAttribute("statistics", statistics);
        model.addAttribute("category", categoryId);
        model.addAttribute("color", colorId);
        return "remaining/products";
    }
}