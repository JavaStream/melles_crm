package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.service.ColorService;
import com.javastream.melles_crm.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Товары сортируются по id
 */

@Controller
@RequestMapping("/category/{categoryId}/color/{colorId}")
public class ProductController {

    private ColorService colorService;
    private ProductService productService;

    public ProductController(ColorService colorService, ProductService productService) {
        this.colorService = colorService;
        this.productService = productService;
    }

    @GetMapping("/product")
    public String listProducts(@PathVariable("colorId") String colorId,
                               Model model) {

        Color color = colorService.findById(colorId);
        Category category = color.getCategory();

        Product product = new Product();
        product.setColor(color);

        List<Product> products = productService.findAllByColor(color);

        model.addAttribute("products", products);
        model.addAttribute("color", color);
        model.addAttribute("category", category);
        model.addAttribute("newProduct", product);
        return "product";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") Product product,
                             @PathVariable("categoryId") String categoryId,
                             @PathVariable("colorId") String colorId) {

        productService.save(product);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product";
    }

    @GetMapping("/product/edit/{id}")
    public String editProductForm(@PathVariable("colorId") String colorId,
                                  @PathVariable("id") String productId,
                                  Model model) {


        Color color = colorService.findById(colorId);
        Product product = productService.findById(productId);
        Category category = color.getCategory();

        model.addAttribute("category", category);
        model.addAttribute("color", color);
        model.addAttribute("product", product);

        return "productEdit";
    }

    @PostMapping("/product/edit/{id}")
    public String updateColor(@ModelAttribute("product") Product product,
                              @PathVariable("categoryId") String categoryId,
                              @PathVariable("colorId") String colorId) {

        productService.save(product);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("categoryId") String categoryId,
                                @PathVariable("colorId") Long colorId,
                                @PathVariable("id") String productId
    ) {

        productService.deleteById(productId);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product";
    }
}