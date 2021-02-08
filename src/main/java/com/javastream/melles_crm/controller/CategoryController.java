package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.repo.CategoryRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
    Категории сортируются по id
 */

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepositorie categoryRepositorie;

    @GetMapping
    public String selectCategories(Model model) {
        List<Category> categories = categoryRepositorie.findAll();
        List<Category> sortedCategories = categories.stream().sorted(Comparator.comparing(Category::getId)).collect(Collectors.toList());

        model.addAttribute("categories", sortedCategories);
        model.addAttribute("newCategory", new Category());
        return "category";
    }

    @PostMapping("/add")
    public String createCategory(@ModelAttribute("category") Category category, BindingResult bindingResult) {

        categoryRepositorie.save(category);

        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepositorie.findById(id).orElseThrow(IllegalStateException::new);

        model.addAttribute("category", category);

        return "categoryEdit";
    }

    @PostMapping("/udate")
    public String updateCategory(Category category) {
        categoryRepositorie.save(category);

        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryRepositorie.deleteById(id);

        return "redirect:/category";
    }
}