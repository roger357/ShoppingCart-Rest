package com.example.shoppingCar.controller;

import com.example.shoppingCar.model.Product;
import com.example.shoppingCar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Busca todos los productos Disponibles
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllEmployees() {
        List<Product> list = productService.getAllProducts();
        return new ResponseEntity<List<Product>>(list, new HttpHeaders(), HttpStatus.OK);
    }

}
