package com.example.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.Odeljenje;
import com.example.e_dnevnik.entities.PredajeUOdeljenju;

public interface PredajeUOdeljenjuRepository extends CrudRepository<PredajeUOdeljenju, Integer> {

	List<PredajeUOdeljenju> findByOdeljenje(Odeljenje odeljenje);

}
