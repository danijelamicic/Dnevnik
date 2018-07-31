package com.example.e_dnevnik.services;

import org.springframework.http.ResponseEntity;

public interface LozinkaDAO {

	ResponseEntity<?> promenaLozinke(String id, String staraLozinka, String novaLozinka);
	
	

}
