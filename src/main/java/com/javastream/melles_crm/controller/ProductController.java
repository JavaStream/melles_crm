package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.repo.CategoryRepositorie;
import com.javastream.melles_crm.repo.ColorRepositorie;
import com.javastream.melles_crm.repo.ProductRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category/{categoryId}/color/{colorId}")
public class ProductController {

    @Autowired
    private ProductRepositorie productRepositorie;

    @Autowired
    private ColorRepositorie colorRepositorie;

    @GetMapping("/product")
    public String listProducts(@PathVariable("categoryId") Long categoryId,
                               @PathVariable("colorId") Long colorId,
                               Model model) {

        Color color = colorRepositorie.findById(colorId).get();

        model.addAttribute("products", productRepositorie.findAll());
        model.addAttribute("color", color);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("newProduct", new Product());
        return "product";
    }

    @PostMapping("/product/add")
    public String addProduct(@PathVariable("categoryId") String categoryId,
                             @PathVariable("colorId") String colorId,
                             Product product) {

        productRepositorie.save(product);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product";
    }
}
