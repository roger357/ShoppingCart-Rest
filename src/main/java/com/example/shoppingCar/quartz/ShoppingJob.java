package com.example.shoppingCar.quartz;

import com.example.shoppingCar.exception.ShoppingCarException;
import com.example.shoppingCar.model.ShoppingCar;
import com.example.shoppingCar.model.enums.ShoppingCarState;
import com.example.shoppingCar.service.ShoppingCarService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ShoppingJob{

    private static final Logger log = LoggerFactory.getLogger(ShoppingJob.class);

    @Autowired
    private ShoppingCarService service;

    /**
     * Este Job se ejecuta cada 5 Minutos (300000 milisegundos)
     */
    @Scheduled(fixedDelay = 300000)
    public void execute(){
        log.info("Executing Job-1...");
        List<ShoppingCar> shoppingCars = service.getShoppingCarsBefore(new DateTime().toDate());
        deleteCars(shoppingCars);
    }

    private void deleteCars(List<ShoppingCar> shoppingCars) {
        shoppingCars.forEach(aCar -> {
            try {
                service.changeStateToShoppingCar(aCar.getId(), ShoppingCarState.DELETED);
            } catch (ShoppingCarException e) {
                log.info("Error en el JOB-1");
                log.error(String.format("Error Durante elimacion del carrito %s", aCar.getId()));
            }
        });

    }

}
