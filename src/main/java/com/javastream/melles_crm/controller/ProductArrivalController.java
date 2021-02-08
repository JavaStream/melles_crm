package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductArrival;
import com.javastream.melles_crm.repo.ColorRepositorie;
import com.javastream.melles_crm.repo.ProductArrivalRepositorie;
import com.javastream.melles_crm.repo.ProductRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/category/{categoryId}/color/{colorId}/product/{productId}")
public class ProductArrivalController {

    private ProductRepositorie productRepositorie;
    private ColorRepositorie colorRepositorie;
    private ProductArrivalRepositorie arrivalRepositorie;


    public ProductArrivalController(ProductRepositorie productRepositorie, ColorRepositorie colorRepositorie, ProductArrivalRepositorie arrivalRepositorie) {
        this.productRepositorie = productRepositorie;
        this.colorRepositorie = colorRepositorie;
        this.arrivalRepositorie = arrivalRepositorie;
    }

    @GetMapping("/arrivals")
    public String listArrivals(@PathVariable("colorId") Long colorId,
                               @PathVariable("productId") Long productId,
                               Model model) {

        Color color = colorRepositorie.findById(colorId).orElseThrow(IllegalStateException::new);
        Category category = color.getCategory();
        Product product = productRepositorie.findById(productId).orElseThrow(IllegalStateException::new);

        List<ProductArrival> arrivals = arrivalRepositorie.findByProduct(product);

        ProductArrival productArrival = new ProductArrival();
        productArrival.setProduct(product);

        model.addAttribute("product", product);
        model.addAttribute("color", color);
        model.addAttribute("category", category);
        model.addAttribute("arrivals", arrivals);
        model.addAttribute("newArrival", productArrival);
        return "arrivals";
    }

    @PostMapping("/arrivals/add")
    public String addArrival(@ModelAttribute("productArrival") ProductArrival productArrival,
                             @PathVariable("categoryId") String categoryId,
                             @PathVariable("colorId") String colorId,
                             @PathVariable("productId") String productId) {

        arrivalRepositorie.save(productArrival);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product/" + productId + "/arrivals";
    }

    @GetMapping("/arrivals/edit/{arrivalId}")
    public String editArrivalForm(@PathVariable("colorId") String colorId,
                                  @PathVariable("productId") String productId,
                                  @PathVariable("arrivalId") String arrivalId,
                                  Model model) {


        Color color = colorRepositorie.findById(Long.parseLong(colorId)).orElseThrow(IllegalStateException::new);
        ProductArrival arrival = arrivalRepositorie.findById(Long.parseLong(arrivalId)).orElseThrow(IllegalStateException::new);

        Category category = color.getCategory();

        Product product = productRepositorie.findById(Long.parseLong(productId))
                .orElseThrow(IllegalStateException::new);

        model.addAttribute("category", category);
        model.addAttribute("color", color);
        model.addAttribute("product", product);
        model.addAttribute("arrival", arrival);

        return "arrivalEdit";
    }

    @PostMapping("/arrivals/edit/{arrivalId}")
    public String updateArrival(@ModelAttribute("product") ProductArrival arrival,
                              @PathVariable("categoryId") String categoryId,
                              @PathVariable("colorId") String colorId,
                              @PathVariable("productId") String productId) {

        arrivalRepositorie.save(arrival);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product/" + productId + "/arrivals";
    }

    @GetMapping("/arrivals/delete/{arrivalId}")
    public String deleteArrival(@PathVariable("arrivalId") String arrivalId,
                                @PathVariable("categoryId") String categoryId,
                                @PathVariable("colorId") String colorId,
                                @PathVariable("productId") String productId) {
        
        arrivalRepositorie.deleteById(Long.parseLong(arrivalId));

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product/" + productId + "/arrivals";
    }

}