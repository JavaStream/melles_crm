package com.javastream.melles_crm.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(unique = true)
    private String articul;

    @Column(nullable = false)
    private BigDecimal price;

    private Long incomingBalance;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
}
