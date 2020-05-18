package com.example.shoppingCar;

import com.example.shoppingCar.dao.ClientRepository;
import com.example.shoppingCar.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShoppingCarApplication {

	private static final Logger log = LoggerFactory.getLogger(ShoppingCarApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCarApplication.class, args);
	}

}
