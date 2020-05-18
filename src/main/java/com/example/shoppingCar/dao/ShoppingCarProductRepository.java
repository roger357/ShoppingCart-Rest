package com.example.shoppingCar.dao;

import com.example.shoppingCar.model.ShoppingCar;
import com.example.shoppingCar.model.ShoppingCarProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoppingCarProductRepository extends CrudRepository<ShoppingCarProduct, Long> {

    List<ShoppingCarProduct> findAllByShoppingCar_Id(Long shoppingCarId);

    List<ShoppingCarProduct> findAllByShoppingCarAndInside(ShoppingCar shoppingCar, boolean inside);

}
