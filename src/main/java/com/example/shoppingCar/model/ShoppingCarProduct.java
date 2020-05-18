package com.example.shoppingCar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name="SHOPPING_CAR_PRODUCT")
@Where(clause="INSIDE=1")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ShoppingCarProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="SHOPPING_CAR_ID")
    private ShoppingCar shoppingCar;

    @Column(name="PRODUCT_ID")
    private Long productid;

    @Column(name="NAME")
    private String name;

    @Column(name = "INSIDE")
    private boolean inside;

    @Column(name="VALUE", nullable=false)
    private Double value;

    public ShoppingCarProduct(ShoppingCar shoppingCar, Long productid, String name,Double value) {
        this.shoppingCar = shoppingCar;
        this.productid = productid;
        this.name = name;
        this.value = value;
        // Por defecto se crea dentro de un carrito
        this.inside = true;
    }
}
