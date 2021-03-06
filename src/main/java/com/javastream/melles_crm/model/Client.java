package com.javastream.melles_crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private ClientType clientType;

    @ElementCollection
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "phone")
    private List<String> phones;

    private String email;
    private String website;
    private String note;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Order> orders;

    @ElementCollection
    @CollectionTable(name = "deliveries", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "delivery")
    private List<String> deliveries;
}