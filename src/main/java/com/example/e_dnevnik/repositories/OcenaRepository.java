package com.example.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.Ocena;
import com.example.e_dnevnik.entities.PredajeUOdeljenju;
import com.example.e_dnevnik.entities.Predmet;
import com.example.e_dnevnik.entities.Ucenik;

public interface OcenaRepository extends CrudRepository<Ocena, Integer> {

	List<Ocena> findByUcenik(Ucenik ucenik);

	List<Ocena> findByPredajeUOdeljenjuAndUcenik(PredajeUOdeljenju nastavnikPredmet, Ucenik ucenik);

	List<Ocena> findByUcenik_Odeljenje_PredajeUOdeljenju_Predaje_Predmet(Predmet predmet);

	List<Ocena> findByUcenikAndPredajeUOdeljenju_Predaje_Predmet(Ucenik ucenik, Predmet predmet);

}
