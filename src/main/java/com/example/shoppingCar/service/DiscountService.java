package com.example.shoppingCar.service;

import com.example.shoppingCar.dao.DiscountRepository;
import com.example.shoppingCar.dao.DiscountRuleRepository;
import com.example.shoppingCar.model.Discount;
import com.example.shoppingCar.model.DiscountRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    @Autowired
    DiscountRepository repository;
    @Autowired
    DiscountRuleRepository discountRuleRepository;

    public Discount findDiscountByProductsQuantity(Integer products) {
        return repository.getDiscountByProductsLimit(products);
    }

    public List<DiscountRule> findRulesByDiscount(Discount discount) {
        discountRuleRepository.getAllByDiscount(discount);
        return discountRuleRepository.getAllByDiscountEquals(discount);
    }


}
