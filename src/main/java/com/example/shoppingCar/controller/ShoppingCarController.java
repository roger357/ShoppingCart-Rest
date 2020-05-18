package com.example.shoppingCar.controller;


import com.example.shoppingCar.exception.ShoppingCarException;
import com.example.shoppingCar.model.ShoppingCar;
import com.example.shoppingCar.model.enums.ShoppingCarState;
import com.example.shoppingCar.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/shoppingcar")
public class ShoppingCarController {

    @Autowired
    private ShoppingCarService shoppingCarService;

    /**
     * Busca todos los carritos asociados a un cliente
     */
    @GetMapping("/all/{clientId}")
    public ResponseEntity<?> getAllCars(@PathVariable("clientId") Long clientId) {
        List<ShoppingCar> shoppingCars = null;
        try {
            shoppingCars = shoppingCarService.getClientShoppingCars(clientId);
            return new ResponseEntity<List<ShoppingCar>>(shoppingCars, new HttpHeaders(), HttpStatus.OK);
        } catch (ShoppingCarException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca todos los productos pertenecientes a un carrito
     */
    @GetMapping("/query/{carId}")
    public ResponseEntity<?> getAllCarProducts(@PathVariable("carId") Long carId) {
        ShoppingCar shoppingCar = null;
        try {
            shoppingCar = shoppingCarService.getShoppingCarById(carId);
            return new ResponseEntity<ShoppingCar>(shoppingCar, new HttpHeaders(), HttpStatus.OK);
        } catch (ShoppingCarException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crea un nuevo Carrito de compras
     * @param id
     */
    @GetMapping("/create/{clientId}")
    public ResponseEntity<?> createNewShoppingCar(@PathVariable("clientId") Long id) {
        ShoppingCar shoppingCar = null;
        try {
            shoppingCar = shoppingCarService.createNewShoppingCar(id);
            return new ResponseEntity<>(shoppingCar, new HttpHeaders(), HttpStatus.OK);
        } catch (ShoppingCarException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Elimina un carrito de compras
     * @param carId  El Id del carrito
     */
    @DeleteMapping("/delete/{carId}" )
    public ResponseEntity<?> deleteShoppingCar(@PathVariable("carId") Long carId) {
        try {
            shoppingCarService.changeStateToShoppingCar(carId, ShoppingCarState.DELETED);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (ShoppingCarException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Aplica la compra al carrito
     * @param carId  El Id del carrito
     */
    @GetMapping("/purchase/{carId}")
    public ResponseEntity<?> payShoppingCar(@PathVariable("carId") Long carId) {
        ShoppingCar shoppingCar = null;
        try {
            shoppingCar = shoppingCarService.changeStateToShoppingCar(carId, ShoppingCarState.PURCHASED);
            return new ResponseEntity<>(shoppingCar, new HttpHeaders(), HttpStatus.OK);
        } catch (ShoppingCarException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Agrega productos al carrito
     * @param carId     Id del carrito
     * @param prodId    Id del producto
     */
    @GetMapping("/addproduct/{carId}/{prodId}")
    public ResponseEntity<?> addProductToShoppingCar(@PathVariable("carId") Long carId, @PathVariable("prodId") Long prodId) {
        ShoppingCar shoppingCar = null;
        try {
            shoppingCar = shoppingCarService.addProductToShoppingCar(carId, prodId);
            return new ResponseEntity<>(shoppingCar, new HttpHeaders(), HttpStatus.OK);
        } catch (ShoppingCarException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Elimina productos del carrito
     * @param carId         Id del Carrito de compras
     * @param carprodId     ID del producto dentro del carrito (Tabla SHOPPING_CAR_PRODUCT)
     */
    @GetMapping("/removeproduct/{carId}/{carprodId}")
    public ResponseEntity<?> removeProductFromShoppingCar(@PathVariable("carId") Long carId, @PathVariable("carprodId") Long carprodId) {
        ShoppingCar shoppingCar = null;
        try {
            shoppingCar = shoppingCarService.removeProductFromShoppingCar(carId, carprodId);
            return new ResponseEntity<>(shoppingCar, new HttpHeaders(), HttpStatus.OK);
        } catch (ShoppingCarException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
