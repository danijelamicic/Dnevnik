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
import com.example.e_dnevnik.entities.Odeljenje;
import com.example.e_dnevnik.entities.Predaje;
import com.example.e_dnevnik.entities.PredajeUOdeljenju;
import com.example.e_dnevnik.entities.dto.PredajeUOdeljenjuDTO;
import com.example.e_dnevnik.repositories.OdeljenjeRepository;
import com.example.e_dnevnik.repositories.PredajeRepository;
import com.example.e_dnevnik.repositories.PredajeUOdeljenjuRepository;




@RestController
@RequestMapping(path = "/dnevnik/predajeuodeljenju")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PredajeUOdeljenjuController {

	@Autowired
	PredajeUOdeljenjuRepository predajeUOdeljenjuRepository;

	@Autowired
	OdeljenjeRepository odeljenjeRepository;

	@Autowired
	PredajeRepository predajeRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSvePredajeUOdeljenju() {
		return new ResponseEntity<Iterable<PredajeUOdeljenju>>(predajeUOdeljenjuRepository.findAll(), HttpStatus.OK);
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNoviPredajeUOdeljenju(@Valid @RequestBody PredajeUOdeljenjuDTO noviPredajeUOdeljenju,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (noviPredajeUOdeljenju.getIdPredaje() == null || noviPredajeUOdeljenju.getIdOdeljenje() == null) {
				return new ResponseEntity<RESTError>(new RESTError("Objekat Predaje nije isparavan"),
						HttpStatus.BAD_REQUEST);
			}
			if (!predajeRepository.findById(noviPredajeUOdeljenju.getIdPredaje()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predaje sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Predaje predaje = predajeRepository.findById(noviPredajeUOdeljenju.getIdPredaje()).get();

			if (!odeljenjeRepository.findById(noviPredajeUOdeljenju.getIdOdeljenje()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.BAD_REQUEST);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(noviPredajeUOdeljenju.getIdOdeljenje()).get();

			PredajeUOdeljenju predajeUOdeljenju = new PredajeUOdeljenju();
			predajeUOdeljenju.setPredaje(predaje);
			predajeUOdeljenju.setOdeljenje(odeljenje);

			predajeUOdeljenjuRepository.save(predajeUOdeljenju);

			return new ResponseEntity<PredajeUOdeljenju>(predajeUOdeljenju, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniPredajeUOdeljenju(@PathVariable String id,
			@Valid @RequestBody PredajeUOdeljenjuDTO izmenjenPredajeUOdeljenju, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!predajeUOdeljenjuRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(
						new RESTError("PredajeUOdeljenju sa prosledjenim id nije pronadjen"), HttpStatus.BAD_REQUEST);
			}
			PredajeUOdeljenju predajeUOdeljenju = predajeUOdeljenjuRepository.findById(Integer.parseInt(id)).get();

			if (!predajeRepository.findById(izmenjenPredajeUOdeljenju.getIdPredaje()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predaje sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Predaje predaje = predajeRepository.findById(izmenjenPredajeUOdeljenju.getIdPredaje()).get();

			predajeUOdeljenju.setPredaje(predaje);

			if (!odeljenjeRepository.findById(izmenjenPredajeUOdeljenju.getIdOdeljenje()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(izmenjenPredajeUOdeljenju.getIdOdeljenje()).get();

			predajeUOdeljenju.setOdeljenje(odeljenje);

			predajeUOdeljenjuRepository.save(predajeUOdeljenju);
			return new ResponseEntity<PredajeUOdeljenju>(predajeUOdeljenju, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiPredajeUOdeljenjuPoId(@PathVariable String id) {
		try {
			if (!predajeUOdeljenjuRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(
						new RESTError("PredajeUOdeljenju sa prosledjenim id nije pronadjen"), HttpStatus.NOT_FOUND);
			}
			PredajeUOdeljenju predajeUOdeljenju = predajeUOdeljenjuRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<PredajeUOdeljenju>(predajeUOdeljenju, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiPredajeUOdeljenjuPoId(@PathVariable String id) {
		try {
			if (!predajeUOdeljenjuRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(
						new RESTError("PredajeUOdeljenju sa prosledjenim id nije pronadjen"), HttpStatus.NOT_FOUND);
			}
			PredajeUOdeljenju predajeUOdeljenju = predajeUOdeljenjuRepository.findById(Integer.parseInt(id)).get();

			if (predajeUOdeljenju.getOdeljenje() != null || predajeUOdeljenju.getPredaje() != null
					|| !predajeUOdeljenju.getOcene().isEmpty()) {
				return new ResponseEntity<RESTError>(
						new RESTError("Brisanje PredajeUOdeljenju sa prosledjenim id nije dozvoljeno"),
						HttpStatus.BAD_REQUEST);
			}

			predajeUOdeljenjuRepository.delete(predajeUOdeljenju);
			return new ResponseEntity<PredajeUOdeljenju>(predajeUOdeljenju, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}