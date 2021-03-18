package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.service.CategoryService;
import com.javastream.melles_crm.service.ColorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/*
    Расцветки (модели) сортируются по id
 */

@Controller
@RequestMapping("/category/{category}")
public class ColorController {

    private ColorService colorService;
    private CategoryService categoryService;

    public ColorController(ColorService colorService, CategoryService categoryService) {
        this.colorService = colorService;
        this.categoryService = categoryService;
    }

    @GetMapping("/color")
    public String selectColor(@PathVariable("category") String categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        List<Color> colors = colorService.findAllVisible(category);

        model.addAttribute("colors", colors);
        model.addAttribute("category", category);
        model.addAttribute("newColor", new Color());
        return "stock/color";
    }

    @PostMapping("/color/add")
    public String addColor(@PathVariable("category") String categoryId, Color color) {
        colorService.save(color);

        return "redirect:/category/" + categoryId + "/color";
    }

    @GetMapping("/color/edit/{id}")
    public String editColorForm(@PathVariable("id") String colorId, Model model) {
        Color color = colorService.findById(colorId);
        Category category = color.getCategory();

        model.addAttribute("color", color);
        model.addAttribute("category", category);

        return "stock/colorEdit";
    }

    @PostMapping("/color/edit/{id}")
    public String updateColor(@PathVariable("category") String categoryId, Color color) {
        colorService.save(color);

        return "redirect:/category/" + categoryId + "/color";
    }

    @GetMapping("/color/delete/{id}")
    public String deleteColor(@PathVariable("category") String categoryId,
                              @PathVariable("id") String colorId) {

        colorService.disableById(colorId);

        return "redirect:/category/" + categoryId + "/color";
    }
}