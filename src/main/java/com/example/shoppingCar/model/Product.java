package com.example.shoppingCar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME", nullable=false, length=50)
    private String name;

    @Column(name="DESCRIPTION", nullable=false)
    private String description;

    @Column(name="VALUE", nullable=false)
    private Double value;

    public Product(String name, String description, double value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
