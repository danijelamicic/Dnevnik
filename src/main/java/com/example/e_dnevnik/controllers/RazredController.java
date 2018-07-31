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
import com.example.e_dnevnik.entities.Razred;
import com.example.e_dnevnik.entities.dto.RazredDTO;
import com.example.e_dnevnik.repositories.RazredRepository;



@RestController
@RequestMapping(path = "/dnevnik/razredi")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class RazredController {

	@Autowired
	RazredRepository razredRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSveRazrede() {
		return new ResponseEntity<Iterable<Razred>>(razredRepository.findAll(), HttpStatus.OK);
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNoviRazred(@Valid @RequestBody RazredDTO noviRazred, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (noviRazred == null) {
				return new ResponseEntity<RESTError>(new RESTError("Objekat razred nije isparavan"),
						HttpStatus.BAD_REQUEST);
			}
			for (Razred r : razredRepository.findAll()) {
				if (r.getVrednost() == noviRazred.getVrednost()) {

					return new ResponseEntity<RESTError>(new RESTError("Razred sa zadatom vrednošću već postoji !"),
							HttpStatus.BAD_REQUEST);
				}
			}
			Razred razred = new Razred();
			razred.setVrednost(noviRazred.getVrednost());

			razredRepository.save(razred);

			return new ResponseEntity<Razred>(razred, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniRazred(@PathVariable String id, @Valid @RequestBody RazredDTO izmenjenRazred,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!razredRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Razred sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Razred razred = razredRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenRazred.getVrednost() != null || !izmenjenRazred.getVrednost().toString().equals(" ")
					|| !izmenjenRazred.getVrednost().toString().equals("")) {
				razred.setVrednost(izmenjenRazred.getVrednost());
			}
			razredRepository.save(razred);
			return new ResponseEntity<Razred>(razred, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiRazredPoId(@PathVariable String id) {
		try {
			if (!razredRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Razred sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Razred razred = razredRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Razred>(razred, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiRazredPoId(@PathVariable String id) {
		try {
			if (!razredRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Razred sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Razred razred = razredRepository.findById(Integer.parseInt(id)).get();
			if (!razred.getOdeljenja().isEmpty() || !razred.getPredmetiURazredu().isEmpty()) {
				return new ResponseEntity<RESTError>(
						new RESTError("Nije dozvoljeno brisanje razreda sa prosleđenim id !"), HttpStatus.BAD_REQUEST);
			}

			razredRepository.delete(razred);
			return new ResponseEntity<Razred>(razred, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
