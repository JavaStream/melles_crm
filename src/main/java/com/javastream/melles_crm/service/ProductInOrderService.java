package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductInOrder;
import com.javastream.melles_crm.repo.ProductInOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInOrderService {

    private ProductInOrderRepository productInOrderRepository;

    public ProductInOrderService(ProductInOrderRepository productInOrderRepository) {
        this.productInOrderRepository = productInOrderRepository;
    }

    public List<ProductInOrder> findAllByProduct(Product product) {
        List<ProductInOrder> products = productInOrderRepository.findByProduct(product);
        return products;
    }

    public ProductInOrder findById(String id) {
        Long productId = Long.parseLong(id);
        return productInOrderRepository.findById(productId)
                .orElseThrow(IllegalStateException::new);
    }

    public void save(ProductInOrder productInOrder) {
        productInOrderRepository.save(productInOrder);
    }
}