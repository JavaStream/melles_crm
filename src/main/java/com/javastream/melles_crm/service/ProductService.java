package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.repo.ProductRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepositorie productRepositorie;

    public ProductService(ProductRepositorie productRepositorie) {
        this.productRepositorie = productRepositorie;
    }

    public List<Product> findAllByColor(Color color) {
        List<Product> products = productRepositorie.findByColor(color);
        List<Product> sortedProducts = products.stream().sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
        return sortedProducts;
    }

    public Product findById(String id) {
        return productRepositorie.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(Product product) {
        productRepositorie.save(product);
    }

    public void deleteById(String id) {
        productRepositorie.deleteById(Long.parseLong(id));
    }
}
