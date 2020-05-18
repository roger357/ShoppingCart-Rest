package com.example.shoppingCar.dao;

import com.example.shoppingCar.model.Discount;
import com.example.shoppingCar.model.DiscountRule;

import java.util.List;

public interface DiscountRuleRepository extends AbstractRepository<DiscountRule> {

    DiscountRule getFirstByDiscount(Discount discount);

    List<DiscountRule> getAllByDiscountEquals(Discount discount);

    List<DiscountRule> getAllByDiscount(Discount discount);

}
