package com.example.shoppingCar.service;

import com.example.shoppingCar.dao.ProductRepository;
import com.example.shoppingCar.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        List<Product> clients = repository.findAll();
        if(clients.size() > 0) {
            return clients;
        } else {
            return new ArrayList<Product>();
        }
    }

    public Product getProduct(long id) {
        Optional productOpt = repository.findById(id);
        if(productOpt.isPresent()){
            return (Product) productOpt.get();
        }
        return null;
    }

}
