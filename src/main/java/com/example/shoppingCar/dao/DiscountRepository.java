package com.example.shoppingCar.dao;

import com.example.shoppingCar.model.Discount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountRepository extends AbstractRepository<Discount> {

    @Query(value = "SELECT * FROM  DISCOUNT ds where (ds.COUNT_TYPE = 0 AND ds.PRODUCT_COUNT_LIMIT = :products) " +
            "OR (ds.COUNT_TYPE = 1 AND ds.PRODUCT_COUNT_INIT <=  :products AND ds.PRODUCT_COUNT_LIMIT >= :products) " +
            "OR (ds.COUNT_TYPE = 2 AND ds.PRODUCT_COUNT_INIT < :products) AND ACTIVE = 1 ORDER BY ds.id ASC LIMIT 1" , nativeQuery = true)
    Discount getDiscountByProductsLimit(@Param("products") Integer products);

}
