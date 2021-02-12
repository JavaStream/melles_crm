package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.service.CategoryService;
import com.javastream.melles_crm.service.ColorService;
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

    public UtilController(CategoryService categoryService, ColorService colorService, ProductService productService) {
        this.categoryService = categoryService;
        this.colorService = colorService;
        this.productService = productService;
    }

    /*@RequestMapping(value = "/colors")
    public Set getColors(@RequestParam String category) {
        Category cat = categoryService.findById(category);
        List<Color> colors = colorService.findAllByCategory(cat);
        Set<String> set = new HashSet<>();
        for (Color color : colors) {
            set.add(color.getName());
        }

        return set;
    }*/

    @RequestMapping(value = "/colors")
    public String getColors(@RequestParam String categoryId) {
        Category category = categoryService.findById(categoryId);
        List<Color> colors = colorService.findAllByCategory(category);
        Map<Long, String> map = new HashMap<>();
        for (Color color : colors) {
            map.put(color.getId(), color.getName());
        }

        JSONObject resultMap = new JSONObject(map);
        return resultMap.toString();
    }

    @RequestMapping(value = "/products")
    public String getProducts(@RequestParam String colorId) {
        Color color = colorService.findById(colorId);

        List<Product> products = productService.findAllByColor(color);

        Map<Long, String> map = new HashMap<>();
        for (Product product : products) {
            map.put(product.getId(), product.getName());
        }

        JSONObject resultMap = new JSONObject(map);
        return resultMap.toString();
    }
}