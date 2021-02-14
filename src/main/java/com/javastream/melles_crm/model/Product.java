package com.javastream.melles_crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String articul;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long incomingBalance;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;


    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductArrival> productArrival;
}
