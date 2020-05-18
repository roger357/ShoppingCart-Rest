package com.example.shoppingCar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="PROMOTION_DATE")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PromotionDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="PROMO_DESC", nullable=false, length=100)
    private String description;

    @Column(name="PROMO_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private java.util.Date promoDate;

    @Column(name = "PROMO_ACTIVE")
    private boolean promoActive;

    @Column(name = "PROMO_VIP")
    private boolean promoVip;
}
