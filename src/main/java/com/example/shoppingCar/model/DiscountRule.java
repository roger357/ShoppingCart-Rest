package com.example.shoppingCar.model;

import com.example.shoppingCar.model.enums.DiscountType;
import com.example.shoppingCar.model.enums.ShoppingCarType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name="DISCOUNT_RULE")
@Where(clause="ACTIVE=1")
@Getter
@Setter
@NoArgsConstructor
public class DiscountRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="DISCOUNT_ID")
    private Discount discount;

    @Column(name="SHOPPING_CAR_TYPE", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private ShoppingCarType type;

    @Column(name="DISCOUNT_TYPE", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private DiscountType discountType;

    @Column(name="DISCOUNT_VALUE", nullable=false)
    private Double value;

    @Column(name = "ACTIVE")
    private boolean active;

    @Override
    public String toString() {
        return "DiscountRule{" +
                "id=" + id +
                ", discount=" + discount +
                ", type=" + type +
                ", discountType=" + discountType +
                ", value=" + value +
                ", active=" + active +
                '}';
    }
}
