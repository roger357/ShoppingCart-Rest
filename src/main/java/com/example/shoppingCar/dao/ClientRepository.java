package com.example.shoppingCar.dao;

import com.example.shoppingCar.model.Client;

import java.util.List;

public interface ClientRepository extends AbstractRepository<Client> {

    List<Client> findByName(String name);

}
