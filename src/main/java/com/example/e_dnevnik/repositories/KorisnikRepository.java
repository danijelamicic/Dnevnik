package com.example.e_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.Korisnik;

public interface KorisnikRepository extends CrudRepository<Korisnik, Integer> {
	
	 public Korisnik findByEmail (String email);

}
