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
import com.example.e_dnevnik.entities.dto.PredmetDTO;
import com.example.e_dnevnik.repositories.PredmetRepository;
import com.example.e_dnevnik.repositories.PredmetURazreduRepository;
import com.example.e_dnevnik.repositories.UcenikRepository;




@RestController
@RequestMapping(path = "/dnevnik/predmeti")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PredmetController {

	@Autowired
	PredmetRepository predmetRepository;
	@Autowired
	UcenikRepository ucenikRepository;
	@Autowired
	PredmetURazreduRepository predmetURazreduRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSvePredmete() {
		return new ResponseEntity<Iterable<Predmet>>(predmetRepository.findAll(), HttpStatus.OK);
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNoviPredmet(@Valid @RequestBody PredmetDTO noviPredmet, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (noviPredmet == null) {
				return new ResponseEntity<RESTError>(new RESTError("Objekat Predmet nije isparavan"),
						HttpStatus.BAD_REQUEST);
			}

			Predmet predmet = new Predmet();
			for (Predmet p : predmetRepository.findAll()) {
				if (p.getNazivPredmeta().equalsIgnoreCase(noviPredmet.getNazivPredmeta())) {
					return new ResponseEntity<RESTError>(new RESTError("Predmet sa tim nazivom već postoji u bazi"),
							HttpStatus.BAD_REQUEST);
				}
			}

			predmet.setNazivPredmeta(noviPredmet.getNazivPredmeta());

			predmetRepository.save(predmet);

			return new ResponseEntity<Predmet>(predmet, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniPredmet(@PathVariable String id, @Valid @RequestBody PredmetDTO izmenjenPredmet,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!predmetRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predmet sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Predmet predmet = predmetRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenPredmet.getNazivPredmeta() != null || !izmenjenPredmet.getNazivPredmeta().equals(" ")
					|| !izmenjenPredmet.getNazivPredmeta().equals("")) {
				predmet.setNazivPredmeta(izmenjenPredmet.getNazivPredmeta());
			}
			predmetRepository.save(predmet);
			return new ResponseEntity<Predmet>(predmet, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@Secured({"ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ"})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiPredmetPoId(@PathVariable String id) {
		try {
			if (!predmetRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predmet sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Predmet predmet = predmetRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Predmet>(predmet, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMINISTRATOR"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiPredmetPoId(@PathVariable String id) {
		try {
			if (!predmetRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Predmet sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Predmet predmet = predmetRepository.findById(Integer.parseInt(id)).get();
			if (!predmet.getPredaje().isEmpty() || !predmet.getPredmetiURazredu().isEmpty()) {
				return new ResponseEntity<RESTError>(
						new RESTError("Brisanje predmeta sa prosledjenim id nije dozvoljeno"), HttpStatus.BAD_REQUEST);
			}

			predmetRepository.delete(predmet);
			return new ResponseEntity<Predmet>(predmet, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "/ucenikSlusa/{idUcenika}")
	private ResponseEntity<?> prikaziSvePredmeteUcenika(@PathVariable String idUcenika) {
		try {
			if (!ucenikRepository.findById(Integer.parseInt(idUcenika)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);

			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(idUcenika)).get();
			Razred razred = ucenik.getRazred();
			List<PredmetURazredu> prURaz = predmetURazreduRepository.findByRazred(razred);
			List<Predmet> predmetiUcenika = new ArrayList<>();
			for (PredmetURazredu p : prURaz) {
				if (p.getRazred().equals(ucenik.getRazred())) {
					predmetiUcenika.add(p.getPredmet());
					predmetRepository.saveAll(predmetiUcenika);
				}
			}
			return new ResponseEntity<Iterable<Predmet>>(predmetiUcenika, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}*/

}