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
import com.example.e_dnevnik.entities.Predmet;
import com.example.e_dnevnik.entities.PredmetURazredu;
import com.example.e_dnevnik.entities.Razred;
import com.example.e_dnevnik.entities.dto.PredmetURazreduDTO;
import com.example.e_dnevnik.repositories.PredmetRepository;
import com.example.e_dnevnik.repositories.PredmetURazreduRepository;
import com.example.e_dnevnik.repositories.RazredRepository;



@RestController
@RequestMapping(path = "/dnevnik/predmetiURazredima")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PredmetURazreduController {

	@Autowired
	PredmetURazreduRepository prRazredRepository;

	@Autowired
	PredmetRepository predmetRepository;

	@Autowired
	RazredRepository razredRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSvePredmeteURazredu() {
		return new ResponseEntity<Iterable<PredmetURazredu>>(prRazredRepository.findAll(), HttpStatus.OK);
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNoviPredmetURazredu(@Valid @RequestBody PredmetURazreduDTO noviPredmetURazredu,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (noviPredmetURazredu.getNedeljniFond() == null || noviPredmetURazredu.getIdPredmet() == null
					|| noviPredmetURazredu.getIdRazred() == null) {
				return new ResponseEntity<RESTError>(new RESTError("Objekat PredmetURazredu nije isparavan"),
						HttpStatus.BAD_REQUEST);
			}
			if (!predmetRepository.findById(noviPredmetURazredu.getIdPredmet()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predmet sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Predmet predmet = predmetRepository.findById(noviPredmetURazredu.getIdPredmet()).get();
			if (!predmetRepository.findById(noviPredmetURazredu.getIdRazred()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Razred sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Razred razred = razredRepository.findById(noviPredmetURazredu.getIdRazred()).get();

			PredmetURazredu prRazred = new PredmetURazredu();
			prRazred.setNedeljniFond(noviPredmetURazredu.getNedeljniFond());
			prRazred.setPredmet(predmet);
			prRazred.setRazred(razred);

			prRazredRepository.save(prRazred);

			return new ResponseEntity<PredmetURazredu>(prRazred, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greäka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniPredmetURazredu(@PathVariable String id,
			@Valid @RequestBody PredmetURazreduDTO izmenjenPredmetURazredu, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!prRazredRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("PredmetURazredu sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			PredmetURazredu prRazred = prRazredRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenPredmetURazredu.getNedeljniFond() != null
					|| !izmenjenPredmetURazredu.getNedeljniFond().toString().equals(" ")
					|| !izmenjenPredmetURazredu.getNedeljniFond().toString().equals("")) {
				prRazred.setNedeljniFond(izmenjenPredmetURazredu.getNedeljniFond());
			}
			if (!predmetRepository.findById(izmenjenPredmetURazredu.getIdPredmet()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predmet sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Predmet predmet = predmetRepository.findById(izmenjenPredmetURazredu.getIdPredmet()).get();
			prRazred.setPredmet(predmet);

			if (!razredRepository.findById(izmenjenPredmetURazredu.getIdRazred()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Razred sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Razred razred = razredRepository.findById(izmenjenPredmetURazredu.getIdRazred()).get();
			prRazred.setRazred(razred);

			prRazredRepository.save(prRazred);
			return new ResponseEntity<PredmetURazredu>(prRazred, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greöka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiPredmeteURazreduPoId(@PathVariable String id) {
		try {
			if (!prRazredRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("PredmetURazredu sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			PredmetURazredu prRazred = prRazredRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<PredmetURazredu>(prRazred, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greöka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiPredmetURazreduPoId(@PathVariable String id) {
		try {
			if (!prRazredRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("PredmetURazredu sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			PredmetURazredu prRazred = prRazredRepository.findById(Integer.parseInt(id)).get();
			if (prRazred.getNedeljniFond() != null || prRazred.getPredmet() != null || prRazred.getRazred() != null) {
				return new ResponseEntity<RESTError>(new RESTError("Brisanje PredmetURazredu nije dozvoljeno"),
						HttpStatus.BAD_REQUEST);
			}

			prRazredRepository.delete(prRazred);
			return new ResponseEntity<PredmetURazredu>(prRazred, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greöka"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}