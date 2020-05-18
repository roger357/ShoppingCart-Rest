package com.example.shoppingCar.model;

import com.example.shoppingCar.model.enums.ShoppingCarState;
import com.example.shoppingCar.model.enums.ShoppingCarType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="SHOPPING_CAR")
@Where(clause="STATE<>2")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ShoppingCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @Column(name="SHOPPING_CAR_TYPE", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private ShoppingCarType type;

    @Column(name="STATE", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private ShoppingCarState state;

    @JsonManagedReference
    @OneToMany(mappedBy = "shoppingCar", cascade = CascadeType.ALL)
    private List<ShoppingCarProduct> products;

    @Column(name="CREATION_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date creationDateTime;

    @Column(name="VALUE", nullable=false)
    private Double value;

    public ShoppingCar(Client client, ShoppingCarType type) {
        this.client = client;
        this.type = type;
        this.state = ShoppingCarState.PENDING;
        this.creationDateTime = Calendar.getInstance().getTime();
        this.value = 0.0;
    }
}
