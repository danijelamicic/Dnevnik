package com.example.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.PredmetURazredu;
import com.example.e_dnevnik.entities.Razred;

public interface PredmetURazreduRepository extends CrudRepository<PredmetURazredu, Integer> {

	List<PredmetURazredu> findByRazred(Razred razred);

}
