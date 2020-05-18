package com.example.shoppingCar.dao;

import com.example.shoppingCar.model.Product;

import java.util.List;

public interface ProductRepository extends AbstractRepository<Product>{

    List<Product> findByName(String name);

}
