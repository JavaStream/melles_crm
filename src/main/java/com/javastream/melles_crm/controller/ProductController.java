package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductArrival;
import com.javastream.melles_crm.repo.ColorRepositorie;
import com.javastream.melles_crm.repo.ProductRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
    Товары сортируются по id
 */

@Controller
@RequestMapping("/category/{categoryId}/color/{colorId}")
public class ProductController {

    @Autowired
    private ProductRepositorie productRepositorie;

    @Autowired
    private ColorRepositorie colorRepositorie;


    @GetMapping("/product")
    public String listProducts(@PathVariable("colorId") Long colorId,
                               Model model) {

        Color color = colorRepositorie.findById(colorId).get();
        Category category = color.getCategory();

        Product product = new Product();
        product.setColor(color);

        List<Product> products = productRepositorie.findByColor(color);
        List<Product> sortedProducts =products.stream().sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());

        model.addAttribute("products", sortedProducts);
        model.addAttribute("color", color);
        model.addAttribute("category", category);
        model.addAttribute("newProduct", product);
        return "product";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") Product product,
                             @PathVariable("categoryId") String categoryId,
                             @PathVariable("colorId") String colorId) {

        productRepositorie.save(product);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product";
    }

    @GetMapping("/product/edit/{id}")
    public String editProductForm(@PathVariable("colorId") String colorId,
                                  @PathVariable("id") String productId,
                                  Model model) {


        Color color = colorRepositorie.findById(Long.parseLong(colorId)).orElseThrow(IllegalStateException::new);

        Category category = color.getCategory();

        Product product = productRepositorie.findById(Long.parseLong(productId))
                .orElseThrow(IllegalStateException::new);

        model.addAttribute("category", category);
        model.addAttribute("color", color);
        model.addAttribute("product", product);

        return "productEdit";
    }

    @PostMapping("/product/edit/{id}")
    public String updateColor(@ModelAttribute("product") Product product,
                              @PathVariable("categoryId") String categoryId,
                              @PathVariable("colorId") String colorId) {

        productRepositorie.save(product);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("categoryId") String categoryId,
                                @PathVariable("colorId") Long colorId,
                                @PathVariable("id") String productId
    ) {

        productRepositorie.deleteById(Long.parseLong(productId));

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product";
    }
}