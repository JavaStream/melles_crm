package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Category;
import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.repo.ColorRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorService {

    private ColorRepository colorRepository;
    private CategoryService categoryService;

    public ColorService(ColorRepository colorRepository, CategoryService categoryService) {
        this.colorRepository = colorRepository;
        this.categoryService = categoryService;
    }

    public List<Color> findAllByCategory(Category category) {
        List<Color> colors = colorRepository.findByCategory(category);
        List<Color> sortedColors = colors.stream().sorted(Comparator.comparing(Color::getId)).collect(Collectors.toList());
        return sortedColors;
    }

    public List<Color> findAllVisible(Category category) {
        List<Color> colors = colorRepository.findByCategory(category);
        List<Color> sortedColors = colors.stream().filter(color -> color.isVisible()).sorted(Comparator.comparing(Color::getId)).collect(Collectors.toList());
        return sortedColors;
    }

    public Color findById(String id) {
        return colorRepository.findById(Long.parseLong(id)).get();
    }

    public void save(Color color) {
        color.setVisible(true);
        colorRepository.save(color);
    }

    public void disableById(String id) {
        Color color = findById(id);
        color.setVisible(false);
        colorRepository.save(color);
    }
}
