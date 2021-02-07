package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.repo.CategoryRepositorie;
import com.javastream.melles_crm.repo.ColorRepositorie;
import com.javastream.melles_crm.repo.ProductRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        Category category = color.getCategory();

        model.addAttribute("products", productRepositorie.findByColor(color));
        model.addAttribute("color", color);
        model.addAttribute("category", category);
        model.addAttribute("newProduct", new Product());
        return "product";
    }

    @PostMapping("/product/add")
    public String addProduct(@PathVariable("categoryId") String categoryId,
                             @PathVariable("colorId") String colorId,
                             Product product) {

        Color color = colorRepositorie.findById(Long.parseLong(colorId)).orElseThrow(IllegalStateException::new);
        product.setColor(color);

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
    public String updateColor(@PathVariable("categoryId") String categoryId,
                              @PathVariable("colorId") String colorId,
                              @PathVariable("id") String productId,
                              Product product) {

        Color color = colorRepositorie.findById(Long.parseLong(colorId)).orElseThrow(IllegalStateException::new);

        product.setColor(color);

        productRepositorie.save(product);

        return "redirect:/category/"+categoryId+"/color/" + colorId + "/product";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("categoryId") String categoryId,
                                @PathVariable("colorId") Long colorId,
                                @PathVariable("id") String productId
    ) {

        productRepositorie.deleteById(Long.parseLong(productId));

        return "redirect:/category/"+categoryId+"/color/" + colorId + "/product";
    }


}