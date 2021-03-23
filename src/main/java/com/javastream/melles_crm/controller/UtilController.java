package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductInOrder;
import com.javastream.melles_crm.service.CategoryService;
import com.javastream.melles_crm.service.ColorService;
import com.javastream.melles_crm.service.ProductInOrderService;
import com.javastream.melles_crm.service.ProductService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
    Расцветки (модели) сортируются по id
 */

@RestController
@RequestMapping("/util")
public class UtilController {

    private CategoryService categoryService;
    private ColorService colorService;
    private ProductService productService;
    private ProductInOrderService productInOrderService;

    public UtilController(CategoryService categoryService, ColorService colorService, ProductService productService, ProductInOrderService productInOrderService) {
        this.categoryService = categoryService;
        this.colorService = colorService;
        this.productService = productService;
        this.productInOrderService = productInOrderService;
    }

    @RequestMapping(value = "/colors")
    public String getColors(@RequestParam String categoryId) {
        Category category = categoryService.findById(categoryId);
        List<Color> colors = colorService.findAllVisible(category);
        Map<Long, String> map = new HashMap<>();
        for (Color color : colors) {
            map.put(color.getId(), "Цвет - " + color.getName());
        }

        JSONObject resultMap = new JSONObject(map);
        return resultMap.toString();
    }

    @RequestMapping(value = "/products")
    public String getProducts(@RequestParam String colorId) {
        Color color = colorService.findById(colorId);

        List<Product> products = productService.findAllVisible(color);

        Map<Long, String> map = new HashMap<>();
        for (Product product : products) {
            map.put(product.getId(), "Арт. " + product.getArticul() + " | размер - " +product.getSize());
        }

        JSONObject resultMap = new JSONObject(map);
        return resultMap.toString();
    }

    @RequestMapping(value = "/product")
    public String getProductPrice(@RequestParam String productId) {
        Product product = productService.findById(productId);
        return product.getPrice().toString();
    }
}