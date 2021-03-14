package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.config.FilesConfig;
import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductPhoto;
import com.javastream.melles_crm.service.ColorService;
import com.javastream.melles_crm.service.FileService;
import com.javastream.melles_crm.service.ProductService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/*
    Товары сортируются по id
 */

@Controller
@RequestMapping("/category/{categoryId}/color/{colorId}")
public class ProductController {

    private ColorService colorService;
    private ProductService productService;
    private FileService fileService;

    public ProductController(ColorService colorService, ProductService productService,
                             FileService fileService) {
        this.colorService = colorService;
        this.productService = productService;
        this.fileService = fileService;
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
                             @PathVariable("colorId") String colorId,
                             @RequestParam("fileToUpload") MultipartFile file) {


        String fileName = fileService.getRandomFileName(file);
        fileService.savePhotoOnDisk(file, fileName);

        ProductPhoto photo = new ProductPhoto();
        photo.setFileName(fileName);
        product.setPhoto(photo);

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
    public String updateProduct(@ModelAttribute("product") Product product,
                                @PathVariable("categoryId") String categoryId,
                                @PathVariable("colorId") String colorId,
                                @RequestParam("fileToUpload") MultipartFile file) {

        if (!file.isEmpty()) {

            String fileName = fileService.getRandomFileName(file);
            fileService.savePhotoOnDisk(file, fileName);

            ProductPhoto photo = new ProductPhoto();
            photo.setFileName(fileName);

            product.setPhoto(photo);

        } else {
            ProductPhoto photo = productService.findById(product.getId().toString()).getPhoto();
            product.setPhoto(photo);
        }

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