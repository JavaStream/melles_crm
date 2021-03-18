package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Color;
import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductPhoto;
import com.javastream.melles_crm.repo.ProductPhotoRepositorie;
import com.javastream.melles_crm.repo.ProductRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepositorie productRepositorie;
    private ProductPhotoRepositorie productPhotoRepositorie;

    public ProductService(ProductRepositorie productRepositorie, ProductPhotoRepositorie productPhotoRepositorie) {
        this.productRepositorie = productRepositorie;
        this.productPhotoRepositorie = productPhotoRepositorie;
    }

    public List<Product> findAllByColor(Color color) {
        List<Product> products = productRepositorie.findByColor(color);
        List<Product> sortedProducts = products.stream().sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
        return sortedProducts;
    }

    public List<Product> findAllVisible(Color color) {
        List<Product> products = productRepositorie.findByColor(color);
        List<Product> sortedProducts = products.stream().filter(product -> product.isVisible()).sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());
        return sortedProducts;
    }

    public Product findById(String id) {
        return productRepositorie.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(Product product) {
        product.setVisible(true);
        productRepositorie.save(product);
    }

    public void disableById(String id) {
        Product product = findById(id);
        product.setVisible(false);
        productRepositorie.save(product);
    }
}
