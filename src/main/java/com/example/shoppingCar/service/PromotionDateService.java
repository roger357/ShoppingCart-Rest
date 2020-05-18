package com.example.shoppingCar.service;

import com.example.shoppingCar.dao.PromotionDateRepository;
import com.example.shoppingCar.model.PromotionDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionDateService {

    @Autowired
    PromotionDateRepository repository;

    public PromotionDate getActivePromoByDate(java.util.Date promoDate, boolean promoActive) {
        return repository.getFirstByPromoDateEqualsAndPromoActive(promoDate, promoActive);
    }


}
