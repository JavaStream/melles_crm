package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.Product;
import com.javastream.melles_crm.model.ProductArrival;
import com.javastream.melles_crm.repo.ProductArrivalRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductArrivalService {

    private ProductArrivalRepositorie arrivalRepositorie;

    public ProductArrivalService(ProductArrivalRepositorie arrivalRepositorie) {
        this.arrivalRepositorie = arrivalRepositorie;
    }

    public List<ProductArrival> findAllByProduct(Product product) {
        List<ProductArrival> arrivals = arrivalRepositorie.findByProduct(product);
        List<ProductArrival> sortedArrivals = arrivals.stream().sorted(Comparator.comparing(ProductArrival::getDate).reversed().thenComparing(Comparator.comparing(ProductArrival::getNumber))).collect(Collectors.toList());
        return sortedArrivals;
    }

    public ProductArrival findById(String id) {
        ProductArrival arrival = arrivalRepositorie.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
        return arrival;
    }

    public void save(ProductArrival productArrival) {
        arrivalRepositorie.save(productArrival);
    }

    public void deleteById(String id) {
        arrivalRepositorie.deleteById(Long.parseLong(id));
    }
}
