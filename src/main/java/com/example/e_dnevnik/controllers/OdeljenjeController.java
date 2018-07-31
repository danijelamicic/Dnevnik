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
import org.springframework.web.bind.annotation.RestController;

import com.example.e_dnevnik.controllers.util.RESTError;
import com.example.e_dnevnik.entities.Odeljenje;
import com.example.e_dnevnik.entities.PredajeUOdeljenju;
import com.example.e_dnevnik.entities.Predmet;
import com.example.e_dnevnik.entities.PredmetURazredu;
import com.example.e_dnevnik.entities.Razred;
import com.example.e_dnevnik.entities.Ucenik;
import com.example.e_dnevnik.entities.dto.OdeljenjeDTO;
import com.example.e_dnevnik.repositories.OdeljenjeRepository;
import com.example.e_dnevnik.repositories.PredajeUOdeljenjuRepository;
import com.example.e_dnevnik.repositories.PredmetRepository;
import com.example.e_dnevnik.repositories.PredmetURazreduRepository;
import com.example.e_dnevnik.repositories.RazredRepository;
import com.example.e_dnevnik.repositories.UcenikRepository;




@RestController
@RequestMapping(path = "/dnevnik/odeljenja")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class OdeljenjeController {

	@Autowired
	OdeljenjeRepository odeljenjeRepository;

	@Autowired
	RazredRepository razredRepository;

	@Autowired
	UcenikRepository ucenikRepository;

	@Autowired
	PredajeUOdeljenjuRepository nastavniciRepository;

	@Autowired
	PredmetURazreduRepository predmetURazreduRepository;

	@Autowired
	PredmetRepository predmetRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSvaOdeljenja() {
		return new ResponseEntity<Iterable<Odeljenje>>(odeljenjeRepository.findAll(), HttpStatus.OK);
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNovoOdeljenje(@Valid @RequestBody OdeljenjeDTO novoOdeljenje, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (novoOdeljenje.getVrednost() == null || novoOdeljenje.getIdRazred() == null) {
				return new ResponseEntity<RESTError>(new RESTError("Objekat odeljenje nije isparavan"),
						HttpStatus.BAD_REQUEST);
			}

			if (!razredRepository.findById(novoOdeljenje.getIdRazred()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Razred sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Razred razred = razredRepository.findById(novoOdeljenje.getIdRazred()).get();
			Odeljenje odeljenje = new Odeljenje();
			odeljenje.setVrednost(novoOdeljenje.getVrednost());
			odeljenje.setRazred(razred);
			for (Odeljenje o : odeljenjeRepository.findAll()) {
				if (o.getVrednost() == odeljenje.getVrednost() && o.getRazred() == odeljenje.getRazred()) {
					return new ResponseEntity<RESTError>(new RESTError("Odeljenje već postoji !"),
							HttpStatus.BAD_REQUEST);
				} else {
					odeljenjeRepository.save(odeljenje);
				}
			}

			return new ResponseEntity<Odeljenje>(odeljenje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniOdeljenje(@PathVariable String id,
			@Valid @RequestBody OdeljenjeDTO izmenjenoOdeljenje, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!odeljenjeRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.NOT_FOUND);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenoOdeljenje.getVrednost() != null || !izmenjenoOdeljenje.getVrednost().toString().equals(" ")
					|| !izmenjenoOdeljenje.getVrednost().toString().equals("")) {
				odeljenje.setVrednost(izmenjenoOdeljenje.getVrednost());
			}
			if (!razredRepository.findById(izmenjenoOdeljenje.getIdRazred()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Razred sa prosledjenim id nije pronadjen"),
						HttpStatus.BAD_REQUEST);
			}
			Razred razred = razredRepository.findById(izmenjenoOdeljenje.getIdRazred()).get();
			odeljenje.setRazred(razred);

			odeljenjeRepository.save(odeljenje);
			return new ResponseEntity<Odeljenje>(odeljenje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiOdeljenjePoId(@PathVariable String id) {
		try {
			if (!odeljenjeRepository.findById(Integer.parseInt(id)).isPresent()) {

				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.NOT_FOUND);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Odeljenje>(odeljenje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK"})
	@RequestMapping(method = RequestMethod.GET, value = "/ucenici/{idOdeljenja}")
	public ResponseEntity<?> prikaziSveUcenikeUOdeljenju(@PathVariable String idOdeljenja) {
		try {
			if (!odeljenjeRepository.findById(Integer.parseInt(idOdeljenja)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.NOT_FOUND);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(Integer.parseInt(idOdeljenja)).get();
			List<Ucenik> ucenici = ucenikRepository.findByOdeljenje(odeljenje);
			ucenikRepository.saveAll(ucenici);
			return new ResponseEntity<List<Ucenik>>(ucenici, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR",  "ROLE_NASTAVNIK"})
	@RequestMapping(method = RequestMethod.GET, value = "/nastavnici/{idOdeljenja}")
	public ResponseEntity<?> prikaziSveNastavnikeUOdeljenju(@PathVariable String idOdeljenja) {
		try {
			if (!odeljenjeRepository.findById(Integer.parseInt(idOdeljenja)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.NOT_FOUND);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(Integer.parseInt(idOdeljenja)).get();
			List<PredajeUOdeljenju> nastavnici = nastavniciRepository.findByOdeljenje(odeljenje);
			nastavniciRepository.saveAll(nastavnici);
			return new ResponseEntity<List<PredajeUOdeljenju>>(nastavnici, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiOdeljenjePoId(@PathVariable String id) {
		try {
			if (!odeljenjeRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.NOT_FOUND);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(Integer.parseInt(id)).get();

			if (!odeljenje.getUcenici().isEmpty() || !odeljenje.getPredajeUOdeljenju().isEmpty()) {
				return new ResponseEntity<RESTError>(
						new RESTError("Brisanje Odeljenja sa prosledjenim id nije dozvoljeno"), HttpStatus.BAD_REQUEST);
			}

			odeljenjeRepository.delete(odeljenje);
			return new ResponseEntity<Odeljenje>(odeljenje, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
/*	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET, value = "/predmeti/{idOdeljenja}")
	private ResponseEntity<?> prikaziSvePredmeteUOdeljenju(@PathVariable String idOdeljenja) {
		try {
			if (!odeljenjeRepository.findById(Integer.parseInt(idOdeljenja)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.NOT_FOUND);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById(Integer.parseInt(idOdeljenja)).get();
			Razred razred = odeljenje.getRazred();
			List<PredmetURazredu> prURaz = predmetURazreduRepository.findByRazred(razred);
			List<Predmet> predmetiOdeljenja = new ArrayList<>();
			for (PredmetURazredu p : prURaz) {
				if (p.getRazred().equals(odeljenje.getRazred())) {
					predmetiOdeljenja.add(p.getPredmet());
					predmetRepository.saveAll(predmetiOdeljenja);
				}
		}
			return new ResponseEntity<List<Predmet>>(predmetiOdeljenja, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}*/

}
