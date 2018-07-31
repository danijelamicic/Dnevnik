package com.example.e_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.e_dnevnik.controllers.util.Encryption;
import com.example.e_dnevnik.controllers.util.RESTError;
import com.example.e_dnevnik.entities.Korisnik;
import com.example.e_dnevnik.repositories.KorisnikRepository;

@Service
public class LozinkaDAOImpl implements LozinkaDAO {

	@Autowired
	KorisnikRepository korisnikRepository;

	public ResponseEntity<?> promenaLozinke(String id, String staraLozinka, String novaLozinka) {
		Korisnik korisnik = korisnikRepository.findById(Integer.parseInt(id)).get();
		if (!(Encryption.matchPass(staraLozinka,
				(korisnikRepository.findById(Integer.parseInt(id)).get().getLozinka())))) {

			return new ResponseEntity<RESTError>(new RESTError("Lozinke se ne poklapaju !"), HttpStatus.BAD_REQUEST);
		}

		korisnik.setLozinka(Encryption.getPassEncoded(novaLozinka));
//		if (korisnik.getUloga().equals(ROLE_ADMINISTRATOR) )
		return new ResponseEntity<Korisnik>(korisnikRepository.save(korisnik), HttpStatus.OK);

	}
}
