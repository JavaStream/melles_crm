package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductArrival;
import com.javastream.melles_crm.service.ColorService;
import com.javastream.melles_crm.service.ProductArrivalService;
import com.javastream.melles_crm.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Приход сортируется сначала по дате, затем по номеру накладной.
 */

@Controller
@RequestMapping("/category/{categoryId}/color/{colorId}/product/{productId}")
public class ProductArrivalController {

    private ProductArrivalService arrivalService;
    private ColorService colorService;
    private ProductService productService;

    public ProductArrivalController(ProductArrivalService arrivalService, ColorService colorService, ProductService productService) {
        this.arrivalService = arrivalService;
        this.colorService = colorService;
        this.productService = productService;
    }

    @GetMapping("/arrivals")
    public String listArrivals(@PathVariable("colorId") String colorId,
                               @PathVariable("productId") String productId,
                               Model model) {

        Color color = colorService.findById(colorId);
        Category category = color.getCategory();

        Product product = productService.findById(productId);

        List<ProductArrival> arrivals = arrivalService.findAllByProduct(product);

        ProductArrival productArrival = new ProductArrival();
        productArrival.setProduct(product);

        model.addAttribute("product", product);
        model.addAttribute("color", color);
        model.addAttribute("category", category);
        model.addAttribute("arrivals", arrivals);
        model.addAttribute("newArrival", productArrival);
        return "stock/arrivals";
    }

    @PostMapping("/arrivals/add")
    public String addArrival(@ModelAttribute("productArrival") ProductArrival productArrival,
                             @PathVariable("categoryId") String categoryId,
                             @PathVariable("colorId") String colorId,
                             @PathVariable("productId") String productId) {

        arrivalService.save(productArrival);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product/" + productId + "/arrivals";
    }

    @GetMapping("/arrivals/edit/{arrivalId}")
    public String editArrivalForm(@PathVariable("colorId") String colorId,
                                  @PathVariable("productId") String productId,
                                  @PathVariable("arrivalId") String arrivalId,
                                  Model model) {


        Color color = colorService.findById(colorId);
        ProductArrival arrival = arrivalService.findById(arrivalId);

        Category category = color.getCategory();

        Product product = productService.findById(productId);

        model.addAttribute("category", category);
        model.addAttribute("color", color);
        model.addAttribute("product", product);
        model.addAttribute("arrival", arrival);

        return "stock/arrivalEdit";
    }

    @PostMapping("/arrivals/edit/{arrivalId}")
    public String updateArrival(@ModelAttribute("product") ProductArrival arrival,
                                @PathVariable("categoryId") String categoryId,
                                @PathVariable("colorId") String colorId,
                                @PathVariable("productId") String productId) {

        arrivalService.save(arrival);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product/" + productId + "/arrivals";
    }

    @GetMapping("/arrivals/delete/{arrivalId}")
    public String deleteArrival(@PathVariable("arrivalId") String arrivalId,
                                @PathVariable("categoryId") String categoryId,
                                @PathVariable("colorId") String colorId,
                                @PathVariable("productId") String productId) {

        arrivalService.deleteById(arrivalId);

        return "redirect:/category/" + categoryId + "/color/" + colorId + "/product/" + productId + "/arrivals";
    }
}