package com.example.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.Nastavnik;
import com.example.e_dnevnik.entities.Predaje;

public interface PredajeRepository extends CrudRepository<Predaje, Integer> {

	List<Predaje> findByNastavnik(Nastavnik nastavnik);

}
