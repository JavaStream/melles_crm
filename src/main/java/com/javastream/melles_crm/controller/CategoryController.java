package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Категории сортируются по id
 */

@Controller
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String selectCategories(Model model) {
        List<Category> categories = categoryService.findAllVisible();

        model.addAttribute("categories", categories);
        model.addAttribute("newCategory", new Category());
        return "stock/category";
    }

    @PostMapping("/add")
    public String createCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);

        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") String id, Model model) {
        Category category = categoryService.findById(id);

        model.addAttribute("category", category);

        return "stock/categoryEdit";
    }

    @PostMapping("/update")
    public String updateCategory(Category category) {
        categoryService.save(category);

        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String disableCategory(@PathVariable("id") String id) {
        categoryService.disableById(id);

        return "redirect:/category";
    }
}