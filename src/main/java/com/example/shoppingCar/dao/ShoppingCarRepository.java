package com.example.shoppingCar.dao;

import com.example.shoppingCar.model.Client;
import com.example.shoppingCar.model.ShoppingCar;
import com.example.shoppingCar.model.enums.ShoppingCarState;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ShoppingCarRepository extends AbstractRepository<ShoppingCar> {

    List<ShoppingCar> findAllByClientAndState(Client client, ShoppingCarState state);

    @Modifying
    @Query("update ShoppingCar sc set sc.state = 2 where sc.id = ?1")
    void delete(Long shoppingCarId);

    @Query(value = "SELECT SUM(VALUE) FROM SHOPPING_CAR WHERE CLIENT_ID = :client AND CREATION_DATE BETWEEN :initDate AND :finalDate", nativeQuery = true)
    Double getTotalSpendedInLasdays(@Param("client") Long clientID, @Param("initDate") Date initDate, @Param("finalDate") Date finalDate);

    List<ShoppingCar> findAllByCreationDateTimeBefore(Date initDate);

}
