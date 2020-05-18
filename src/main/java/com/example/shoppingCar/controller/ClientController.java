package com.example.shoppingCar.controller;

import com.example.shoppingCar.model.Client;
import com.example.shoppingCar.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Busca un cliente especifico
     * @return un solo cliente
     */
    @GetMapping("/get/{id}")
    public Client getClient(@PathVariable("id") Long id) {
        return  clientService.getClientById(id);
    }


    /**
     * Busca todos los clientes Disponibles
     * @return
     */
    @GetMapping
    public List<Client> getAllClients() {
        return  clientService.getAllClients();
    }


}
