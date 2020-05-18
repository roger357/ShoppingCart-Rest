package com.example.shoppingCar.dao;

import com.example.shoppingCar.model.PromotionDate;

public interface PromotionDateRepository extends AbstractRepository<PromotionDate>{

    public PromotionDate getFirstByPromoDateEqualsAndPromoActive(java.util.Date promoDate, boolean promoActive);

}
