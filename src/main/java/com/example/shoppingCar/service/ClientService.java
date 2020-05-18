package com.example.shoppingCar.service;

import com.example.shoppingCar.dao.ClientRepository;
import com.example.shoppingCar.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> getAllClients() {
        List<Client> clients = repository.findAll();
        if(clients.size() > 0) {
            return clients;
        } else {
            return new ArrayList<Client>();
        }
    }

    public Client getClientById(Long Id) {
        Optional clientOpt = repository.findById(Id);
        if(clientOpt.isPresent()) {
            return (Client)  clientOpt.get();
        }else {
            return null;
        }
    }

}
