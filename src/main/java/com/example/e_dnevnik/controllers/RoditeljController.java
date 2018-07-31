package com.example.e_dnevnik.controllers;

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
import com.example.e_dnevnik.entities.Roditelj;
import com.example.e_dnevnik.entities.Ucenik;
import com.example.e_dnevnik.entities.dto.RoditeljDTO;
import com.example.e_dnevnik.entities.dto.RoditeljIzmenjenDTO;
import com.example.e_dnevnik.enumerations.EKorisnikUloga;
import com.example.e_dnevnik.repositories.KorisnikRepository;
import com.example.e_dnevnik.repositories.RoditeljRepository;
import com.example.e_dnevnik.repositories.UcenikRepository;
import com.example.e_dnevnik.services.LozinkaDAO;

@RestController
@RequestMapping(path = "/dnevnik/roditelji")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class RoditeljController {

	@Autowired
	RoditeljRepository roditeljRepository;

	@Autowired
	UcenikRepository ucenikRepository;

	@Autowired
	KorisnikRepository korisnikRepository;

	@Autowired
	LozinkaDAO lozinkaDAO;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}

	@Secured("ROLE_RODITELJ")
	@RequestMapping(method = RequestMethod.PUT, value = "/change-password/{id}")
	public ResponseEntity<?> promenaLozinke(@PathVariable String id, @RequestParam String staraLozinka,
			@RequestParam String novaLozinka) {

		return lozinkaDAO.promenaLozinke(id, staraLozinka, novaLozinka);

	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSveRoditelje() {
		return new ResponseEntity<Iterable<Roditelj>>(roditeljRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNovogRoditelja(@Valid @RequestBody RoditeljDTO noviRoditelj, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			Roditelj roditelj = new Roditelj();
			for (Korisnik korisnik : korisnikRepository.findAll()) {
				if (korisnik.getEmail().equals(noviRoditelj.getEmail())) {
					return new ResponseEntity<RESTError>(
							new RESTError("Već postoji registrovan korisnik sa prosledjenim email-om !"),
							HttpStatus.BAD_REQUEST);
				}
			}
			roditelj.setIme(noviRoditelj.getIme());
			roditelj.setPrezime(noviRoditelj.getPrezime());
			roditelj.setKorisnickoIme(noviRoditelj.getKorisnickoIme());
			roditelj.setEmail(noviRoditelj.getEmail());
			roditelj.setLozinka(Encryption.getPassEncoded(noviRoditelj.getLozinka()));
			roditelj.setUloga(EKorisnikUloga.ROLE_RODITELJ);
			roditeljRepository.save(roditelj);

			return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniRoditelja(@PathVariable String id, @Valid @RequestBody RoditeljIzmenjenDTO izmenjenRoditelj,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!roditeljRepository.findById(Integer.parseInt(id)).isPresent()) {

				return new ResponseEntity<RESTError>(new RESTError("Roditelj sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Roditelj roditelj = roditeljRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenRoditelj.getIme() != null || !izmenjenRoditelj.getIme().equals(" ")
					|| !izmenjenRoditelj.getIme().equals("")) {
				roditelj.setIme(izmenjenRoditelj.getIme());
			}
			if (izmenjenRoditelj.getPrezime() != null || !izmenjenRoditelj.getPrezime().equals(" ")
					|| !izmenjenRoditelj.getPrezime().equals("")) {
				roditelj.setPrezime(izmenjenRoditelj.getPrezime());
			}
			if (izmenjenRoditelj.getKorisnickoIme() != null || !izmenjenRoditelj.getKorisnickoIme().equals(" ")
					|| !izmenjenRoditelj.getKorisnickoIme().equals("")) {
				roditelj.setKorisnickoIme(izmenjenRoditelj.getKorisnickoIme());
			}
			if (izmenjenRoditelj.getEmail() != null || !izmenjenRoditelj.getEmail().equals(" ")
					|| !izmenjenRoditelj.getEmail().equals("")) {
				roditelj.setEmail(izmenjenRoditelj.getEmail());
			}

			roditeljRepository.save(roditelj);
			return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiRoditeljaPoId(@PathVariable String id) {
		try {
			if (!roditeljRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Roditelj sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Roditelj roditelj = roditeljRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiRoditeljaPoId(@PathVariable String id) {
		try {
			if (!roditeljRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Roditelj sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Roditelj roditelj = roditeljRepository.findById(Integer.parseInt(id)).get();

			if (!roditelj.getUcenici().isEmpty()) {
				return new ResponseEntity<RESTError>(
						new RESTError("Nije dozvoljeno brisanje roditelja sa prosledjenim id !"),
						HttpStatus.BAD_REQUEST);

			} else {
				roditeljRepository.delete(roditelj);
				return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_RODITELJ" })
	@RequestMapping(method = RequestMethod.GET, value = "/ucenici/{idRoditelja}")
	public ResponseEntity<?> pronadjiDecuRoditelja(@PathVariable String idRoditelja) {
		try {
			if (!roditeljRepository.findById(Integer.parseInt(idRoditelja)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Roditelj sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Roditelj roditelj = roditeljRepository.findById(Integer.parseInt(idRoditelja)).get();
			List<Ucenik> ucenici = ucenikRepository.findByRoditelj(roditelj);

			return new ResponseEntity<Iterable<Ucenik>>(ucenici, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
