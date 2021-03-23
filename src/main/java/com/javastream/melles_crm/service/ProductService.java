package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.repo.ProductPhotoRepository;
import com.javastream.melles_crm.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductPhotoRepository productPhotoRepository;

    public ProductService(ProductRepository productRepository, ProductPhotoRepository productPhotoRepository) {
        this.productRepository = productRepository;
        this.productPhotoRepository = productPhotoRepository;
    }

    public List<Product> findAllByColor(Color color) {
        List<Product> products = productRepository.findByColor(color);
        return products.stream()
                .filter(Product::isVisible)
                .sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
    }

    public List<Product> findAllVisible(Color color) {
        List<Product> products = productRepository.findByColor(color);
        List<Product> sortedProducts = products.stream().filter(product -> product.isVisible()).sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
        return sortedProducts;
    }

    public Product findById(String id) {
        return productRepository.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(Product product) {
        product.setVisible(true);
        productRepository.save(product);
    }

    public void disableById(String id) {
        Product product = findById(id);
        product.setVisible(false);
        productRepository.save(product);
    }
}
