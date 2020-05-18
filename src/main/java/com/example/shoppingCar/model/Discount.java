package com.example.shoppingCar.model;

import com.example.shoppingCar.model.enums.DiscountLimitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="DISCOUNT")
@Where(clause="ACTIVE<>0")
@Getter
@Setter
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "discount")
    private List<DiscountRule> rules;

    @Column(name="COUNT_TYPE", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private DiscountLimitType countType;

    @Column(name="PRODUCT_COUNT_INIT", nullable=false)
    private Long countInit;

    @Column(name="PRODUCT_COUNT_LIMIT", nullable=false)
    private Long countLimit;

    @Column(name = "ACTIVE")
    private boolean active;

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", countType=" + countType +
                ", countInit=" + countInit +
                ", countLimit=" + countLimit +
                ", active=" + active +
                '}';
    }
}
