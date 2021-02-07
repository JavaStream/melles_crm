package com.javastream.melles_crm.controller;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.repo.CategoryRepositorie;
import com.javastream.melles_crm.repo.ColorRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*****************
 *      Colors
 ****************/

@Controller
@RequestMapping("/category/{category}")
public class ColorController {

    @Autowired
    private CategoryRepositorie categoryRepositorie;

    @Autowired
    private ColorRepositorie colorRepositorie;

    @GetMapping("/color")
    public String selectColor(@PathVariable("category") String id, Model model) {
        Category category = categoryRepositorie.findById(Long.parseLong(id)).get();

        model.addAttribute("colors", colorRepositorie.findByCategory(category));
        model.addAttribute("category", category);
        model.addAttribute("newColor", new Color());
        return "color";
    }

    @PostMapping("/color/add")
    public String addColor(@PathVariable("category") String categoryId, Color color) {
        colorRepositorie.save(color);

        return "redirect:/category/" + categoryId + "/color";
    }

    @GetMapping("/color/edit/{id}")
    public String editColorForm(@PathVariable("category") String categoryId, @PathVariable("id") String colorId, Model model) {
        Category category = categoryRepositorie.findById(Long.parseLong(categoryId))
                .orElseThrow(IllegalStateException::new);

        Color color = colorRepositorie.findById(Long.parseLong(colorId))
                .orElseThrow(IllegalStateException::new);

        model.addAttribute("color", color);
        model.addAttribute("category", category);

        return "colorEdit";
    }

    @PostMapping("/color/edit/{id}")
    public String updateColor(@PathVariable("category") String categoryId, Color color) {
        colorRepositorie.save(color);

        return "redirect:/category/" + categoryId + "/color";
    }

    @GetMapping("/color/delete/{id}")
    public String deleteColor(@PathVariable("category") String categoryId,
                              @PathVariable("id") Long colorId) {

        colorRepositorie.deleteById(colorId);

        return "redirect:/category/" + categoryId + "/color";
    }
}