package com.javastream.melles_crm.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Обязательное поле")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Обязательное поле")
    private String description;

    @OneToMany(mappedBy = "category", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Color> colors;
}
