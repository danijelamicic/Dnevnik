package com.example.e_dnevnik.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.e_dnevnik.controllers.util.RESTError;
import com.example.e_dnevnik.entities.Nastavnik;
import com.example.e_dnevnik.entities.Predaje;
import com.example.e_dnevnik.entities.Predmet;
import com.example.e_dnevnik.entities.dto.PredajeDTO;
import com.example.e_dnevnik.repositories.NastavnikRepository;
import com.example.e_dnevnik.repositories.PredajeRepository;
import com.example.e_dnevnik.repositories.PredmetRepository;


@RestController
@RequestMapping(path = "/dnevnik/predaje")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PredajeController {

	@Autowired
	PredajeRepository predajeRepository;

	@Autowired
	NastavnikRepository nastavnikRepository;

	@Autowired
	PredmetRepository predmetRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSvePredaje() {
		return new ResponseEntity<Iterable<Predaje>>(predajeRepository.findAll(), HttpStatus.OK);
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNoviPredaje(@Valid @RequestBody PredajeDTO noviPredaje, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (noviPredaje.getNastavnikId() == null || noviPredaje.getPredmetId() == null) {
				return new ResponseEntity<RESTError>(new RESTError("Objekat Predaje nije isparavan"),
						HttpStatus.BAD_REQUEST);
			}
			if (!nastavnikRepository.findById(noviPredaje.getNastavnikId()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Nastavnik sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Nastavnik nastavnik = nastavnikRepository.findById(noviPredaje.getNastavnikId()).get();
			if (!predmetRepository.findById(noviPredaje.getPredmetId()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predmet sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Predmet predmet = predmetRepository.findById(noviPredaje.getPredmetId()).get();

			Predaje predaje = new Predaje();
			predaje.setNastavnik(nastavnik);
			predaje.setPredmet(predmet);

			predajeRepository.save(predaje);

			return new ResponseEntity<Predaje>(predaje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniPredaje(@PathVariable String id, @Valid @RequestBody PredajeDTO izmenjenPredaje,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!predajeRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predaje sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Predaje predaje = predajeRepository.findById(Integer.parseInt(id)).get();

			if (!nastavnikRepository.findById(izmenjenPredaje.getNastavnikId()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Nastavnik sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Nastavnik nastavnik = nastavnikRepository.findById(izmenjenPredaje.getNastavnikId()).get();
			if (!predmetRepository.findById(izmenjenPredaje.getPredmetId()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predmet sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Predmet predmet = predmetRepository.findById(izmenjenPredaje.getPredmetId()).get();

			predaje.setNastavnik(nastavnik);
			predaje.setPredmet(predmet);

			predajeRepository.save(predaje);

			return new ResponseEntity<Predaje>(predaje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR","ROLE_NASTAVNIK"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiPredajePoId(@PathVariable String id) {
		try {
			if (!predajeRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predaje sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Predaje predaje = predajeRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Predaje>(predaje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiPredajePoId(@PathVariable String id) {
		try {
			if (!predajeRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predaje sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Predaje predaje = predajeRepository.findById(Integer.parseInt(id)).get();
			if (!predaje.getPredajeUOdeljenju().isEmpty() || predaje.getNastavnik() != null
					|| predaje.getPredmet() != null) {
				return new ResponseEntity<RESTError>(
						new RESTError("Nije dozvoljeno brisanje predaje sa prosledjenim id"), HttpStatus.BAD_REQUEST);
			}

			predajeRepository.delete(predaje);
			return new ResponseEntity<Predaje>(predaje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
