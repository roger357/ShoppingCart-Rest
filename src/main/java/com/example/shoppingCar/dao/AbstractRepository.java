package com.example.shoppingCar.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AbstractRepository<T> extends CrudRepository<T, Long> {

    @Override
    List<T> findAll();

}
