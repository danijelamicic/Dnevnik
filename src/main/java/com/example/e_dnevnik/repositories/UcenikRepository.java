package com.example.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.Ocena;
import com.example.e_dnevnik.entities.Odeljenje;
import com.example.e_dnevnik.entities.Roditelj;
import com.example.e_dnevnik.entities.Ucenik;

public interface UcenikRepository extends CrudRepository<Ucenik, Integer> {

	List<Ucenik> findByOdeljenje(Odeljenje odeljenje);

	List<Ucenik> findByRoditelj(Roditelj roditelj);

	
	
	

}
