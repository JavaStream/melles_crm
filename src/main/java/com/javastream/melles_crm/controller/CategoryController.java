package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.repo.CategoryRepositorie;
import com.javastream.melles_crm.repo.ColorRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*****************
 *      Category
 ****************/

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepositorie categoryRepositorie;

    @GetMapping
    public String selectCategories(Model model) {
        model.addAttribute("categories", categoryRepositorie.findAll());
        model.addAttribute("newCategory", new Category());
        return "category";
    }

    @PostMapping
    public String addCategory(Category category) {
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
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        categoryRepositorie.deleteById(id);

        return "redirect:/category";
    }
}