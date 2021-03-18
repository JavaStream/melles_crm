package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.repo.CategoryRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepositorie categoryRepositorie;

    public CategoryService(CategoryRepositorie categoryRepositorie) {
        this.categoryRepositorie = categoryRepositorie;
    }

    public List<Category> findAll() {
        List<Category> categories = categoryRepositorie.findAll();
        List<Category> sortedCategories = categories.stream()
                .sorted(Comparator.comparing(Category::getId)).collect(Collectors.toList());
        return sortedCategories;
    }

    public List<Category> findAllVisible() {
        List<Category> categories = categoryRepositorie.findAll();
        List<Category> sortedCategories = categories.stream()
                .filter(cat -> cat.isVisible())
                .sorted(Comparator.comparing(Category::getId)).collect(Collectors.toList());
        return sortedCategories;
    }

    public Category findById(String id) {
        return categoryRepositorie.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(Category category) {
        category.setVisible(true);
        categoryRepositorie.save(category);
    }

    public void disableById(String id) {
        Category category = findById(id);
        category.setVisible(false);
        categoryRepositorie.save(category);
    }

}
