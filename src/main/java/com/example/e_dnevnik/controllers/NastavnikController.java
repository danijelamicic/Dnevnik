package com.example.e_dnevnik.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_dnevnik.controllers.util.Encryption;
import com.example.e_dnevnik.controllers.util.RESTError;
import com.example.e_dnevnik.entities.Korisnik;
import com.example.e_dnevnik.entities.Nastavnik;
import com.example.e_dnevnik.entities.Odeljenje;
import com.example.e_dnevnik.entities.Predaje;
import com.example.e_dnevnik.entities.PredajeUOdeljenju;
import com.example.e_dnevnik.entities.Predmet;
import com.example.e_dnevnik.entities.dto.NastavnikDTO;
import com.example.e_dnevnik.entities.dto.NastavnikIzmenjenDTO;
import com.example.e_dnevnik.enumerations.EKorisnikUloga;
import com.example.e_dnevnik.repositories.KorisnikRepository;
import com.example.e_dnevnik.repositories.NastavnikRepository;
import com.example.e_dnevnik.repositories.OdeljenjeRepository;
import com.example.e_dnevnik.repositories.PredajeRepository;
import com.example.e_dnevnik.repositories.PredajeUOdeljenjuRepository;
import com.example.e_dnevnik.repositories.PredmetRepository;
import com.example.e_dnevnik.services.LozinkaDAO;

@RestController
@RequestMapping(path = "/dnevnik/nastavnici")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class NastavnikController {

	@Autowired
	NastavnikRepository nastavnikRepository;

	@Autowired
	PredajeRepository predajeRepository;

	@Autowired
	KorisnikRepository korisnikRepository;

	@Autowired
	PredmetRepository predmetRepository;

	@Autowired
	PredajeUOdeljenjuRepository predajeuodeljenjuRepository;

	@Autowired
	OdeljenjeRepository odeljenjeRepository;

	@Autowired
	LozinkaDAO lozinkaDAO;

	// promena lozinke

	@Secured("ROLE_NASTAVNIK")
	@RequestMapping(method = RequestMethod.PUT, value = "/change-password/{id}")
	public ResponseEntity<?> promenaLozinke(@PathVariable String id, @RequestParam String staraLozinka,
			@RequestParam String novaLozinka) {

		return lozinkaDAO.promenaLozinke(id, staraLozinka, novaLozinka);

	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}

// vraća listu nastavnika
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSveNastavnike() {
		return new ResponseEntity<Iterable<Nastavnik>>(nastavnikRepository.findAll(), HttpStatus.OK);
	}

// dodavanje novog nastavnika
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNovogNastavnika(@Valid @RequestBody NastavnikDTO noviNastavnik,
			BindingResult result) {
		try {

			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}

			Nastavnik nastavnik = new Nastavnik();
			for (Korisnik korisnik : korisnikRepository.findAll()) {
				if (korisnik.getEmail().equals(noviNastavnik.getEmail())) {
					return new ResponseEntity<RESTError>(
							new RESTError("VeĆ postoji registrovan korisnik sa prosledjenim email-om !"),
							HttpStatus.BAD_REQUEST);
				}
			}
			nastavnik.setIme(noviNastavnik.getIme());
			nastavnik.setPrezime(noviNastavnik.getPrezime());
			nastavnik.setKorisnickoIme(noviNastavnik.getKorisnickoIme());
			nastavnik.setEmail(noviNastavnik.getEmail());
			nastavnik.setLozinka(Encryption.getPassEncoded(noviNastavnik.getLozinka()));
			nastavnik.setUloga(EKorisnikUloga.ROLE_NASTAVNIK);
			nastavnik.setZvanje(noviNastavnik.getZvanje());

			nastavnikRepository.save(nastavnik);

			return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greŠka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

// izmena nastavnika
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniNastavnika(@PathVariable String id,
			@Valid @RequestBody NastavnikIzmenjenDTO izmenjenNastavnik, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Nastavnik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Nastavnik nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenNastavnik.getIme() != null || !izmenjenNastavnik.getIme().equals(" ")
					|| !izmenjenNastavnik.getIme().equals("")) {
				nastavnik.setIme(izmenjenNastavnik.getIme());
			}
			if (izmenjenNastavnik.getPrezime() != null || !izmenjenNastavnik.getPrezime().equals(" ")
					|| !izmenjenNastavnik.getPrezime().equals("")) {
				nastavnik.setPrezime(izmenjenNastavnik.getPrezime());
			}
			if (izmenjenNastavnik.getKorisnickoIme() != null || !izmenjenNastavnik.getKorisnickoIme().equals(" ")
					|| !izmenjenNastavnik.getKorisnickoIme().equals("")) {
				nastavnik.setKorisnickoIme(izmenjenNastavnik.getKorisnickoIme());
			}
			if (izmenjenNastavnik.getEmail() != null || !izmenjenNastavnik.getEmail().equals(" ")
					|| !izmenjenNastavnik.getEmail().equals("")) {
				nastavnik.setEmail(izmenjenNastavnik.getEmail());
			}
			if (izmenjenNastavnik.getZvanje() != null || !izmenjenNastavnik.getZvanje().equals(" ")
					|| !izmenjenNastavnik.getZvanje().equals("")) {
				nastavnik.setZvanje(izmenjenNastavnik.getZvanje());
			}
			nastavnikRepository.save(nastavnik);
			return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greŠka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

// vraća nastavnika po Id
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiNastavnikaPoId(@PathVariable String id) {
		try {
			if (!nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Nastavnik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Nastavnik nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greŠka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

// brisanje jednog nastavnika
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiNastavnikaPoId(@PathVariable String id) {
		try {

			if (!nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Nastavnik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Nastavnik nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();
			if (!nastavnik.getPredaje().isEmpty()) {
				return new ResponseEntity<RESTError>(
						new RESTError("Nije dozvoljeno brisanje nastavnika sa prosledjenim id"),
						HttpStatus.BAD_REQUEST);
			}

			nastavnikRepository.delete(nastavnik);
			return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greŠka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

// vraća listu predmeta koje jedan nastavnik predaje
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK" })
	@RequestMapping(method = RequestMethod.GET, value = "/predmeti/{id}")
	public ResponseEntity<?> pronadjiSvePredmeteNastavnika(@PathVariable String id) {
		try {
			if (!nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {

				return new ResponseEntity<RESTError>(new RESTError("Nastavnik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Nastavnik nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();

			List<Predaje> predaje = predajeRepository.findByNastavnik(nastavnik);
			List<Predmet> predmetiNastavnika = new ArrayList<>();
			for (Predaje p : predaje) {

				predmetiNastavnika.add(p.getPredmet());
				predmetRepository.saveAll(predmetiNastavnika);
			}
			return new ResponseEntity<Iterable<Predmet>>(predmetiNastavnika, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
// odeljenja u kojima nastavnik predaje
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK" })
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenja/{id}")
	public ResponseEntity<?> pronadjiSvaOdeljenjaNastavnika(@PathVariable String id) {
		try {
			if (!nastavnikRepository.findById(Integer.parseInt(id)).isPresent()) {

				return new ResponseEntity<RESTError>(new RESTError("Nastavnik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Nastavnik nastavnik = nastavnikRepository.findById(Integer.parseInt(id)).get();

			List<Predaje> predaje = predajeRepository.findByNastavnik(nastavnik);
			List<PredajeUOdeljenju> odeljenjaNastavnika = new ArrayList<>();
			for (Predaje p : predaje) {

				if (p.getNastavnik().equals(nastavnik)) {
					odeljenjaNastavnika.addAll(p.getPredajeUOdeljenju());
					predajeuodeljenjuRepository.saveAll(odeljenjaNastavnika);
				}
			}

			List<Odeljenje> odeljenja = new ArrayList<>();
			for (PredajeUOdeljenju po : odeljenjaNastavnika) {
				odeljenja.add(po.getOdeljenje());
				odeljenjeRepository.saveAll(odeljenja);
			}

			return new ResponseEntity<List<Odeljenje>>(odeljenja, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
