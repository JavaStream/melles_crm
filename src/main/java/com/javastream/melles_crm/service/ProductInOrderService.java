package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductInOrder;
import com.javastream.melles_crm.repo.ProductInOrderRepositorie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInOrderService {

    private ProductInOrderRepositorie productInOrderRepositorie;

    public ProductInOrderService(ProductInOrderRepositorie productInOrderRepositorie) {
        this.productInOrderRepositorie = productInOrderRepositorie;
    }

    public List<ProductInOrder> findAllByProduct(Product product) {
        List<ProductInOrder> products = productInOrderRepositorie.findByProduct(product);
        return products;
    }
}