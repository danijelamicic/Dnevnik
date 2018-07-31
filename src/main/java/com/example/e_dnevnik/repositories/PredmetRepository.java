package com.example.e_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.Predmet;

public interface PredmetRepository extends CrudRepository<Predmet, Integer> {

}
