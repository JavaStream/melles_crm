package com.javastream.melles_crm.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "category", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Color> colors;
}
