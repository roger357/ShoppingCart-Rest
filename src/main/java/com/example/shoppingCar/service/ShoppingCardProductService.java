package com.example.shoppingCar.service;

import com.example.shoppingCar.dao.ShoppingCarProductRepository;
import com.example.shoppingCar.model.Product;
import com.example.shoppingCar.model.ShoppingCar;
import com.example.shoppingCar.model.ShoppingCarProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCardProductService {

    @Autowired
    private ShoppingCarProductRepository repository;



    ShoppingCarProduct createNewShoppingCarProduct(ShoppingCar shoppingCar, Product product) {
        ShoppingCarProduct shoppingCarProduct = new ShoppingCarProduct(shoppingCar, product.getId(), product.getName(), product.getValue());
        return repository.save(shoppingCarProduct);
    }

    public boolean existsProductOnCar(Long productCarId) {
        return repository.findById(productCarId).isPresent();
    }

    public void removeProduct(ShoppingCarProduct shoppingCarProduct){
        shoppingCarProduct.setInside(false);
        repository.save(shoppingCarProduct);
    }




}
