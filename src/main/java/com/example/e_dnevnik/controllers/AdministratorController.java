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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_dnevnik.controllers.util.Encryption;
import com.example.e_dnevnik.controllers.util.RESTError;
import com.example.e_dnevnik.entities.Administrator;
import com.example.e_dnevnik.entities.Korisnik;
import com.example.e_dnevnik.entities.dto.AdministratorDTO;
import com.example.e_dnevnik.entities.dto.AdministratorIzmenjenDTO;
import com.example.e_dnevnik.enumerations.EKorisnikUloga;
import com.example.e_dnevnik.repositories.AdminRepository;
import com.example.e_dnevnik.repositories.KorisnikRepository;
import com.example.e_dnevnik.services.LozinkaDAOImpl;

@RestController
@RequestMapping(path = "/dnevnik/admini")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AdministratorController {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	KorisnikRepository korisnikRepository;

	@Autowired
	LozinkaDAOImpl lozinkaDAO;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/change-password/{id}")
	public ResponseEntity<?> promenaLozinke(@PathVariable String id, @RequestParam String staraLozinka,
			@RequestParam String novaLozinka) {

		return lozinkaDAO.promenaLozinke(id, staraLozinka, novaLozinka);

	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSveAdmine() {
		return new ResponseEntity<Iterable<Administrator>>(adminRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiAdminaPoId(@PathVariable String id) {
		try {
			if (!adminRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Administrator sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Administrator admin = adminRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Administrator>(admin, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiAdministratoraPoId(@PathVariable String id) {
		try {

			if (!adminRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Administrator sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Administrator admin = adminRepository.findById(Integer.parseInt(id)).get();
			adminRepository.delete(admin);

			return new ResponseEntity<Administrator>(admin, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNovogAdministratora(@Valid @RequestBody AdministratorDTO noviAdministrator,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			Administrator admin = new Administrator();
			for (Korisnik korisnik : korisnikRepository.findAll()) {
				if (korisnik.getEmail().equals(noviAdministrator.getEmail())) {
					return new ResponseEntity<RESTError>(
							new RESTError("Već postoji registrovan korisnik sa prosledjenim email-om !"),
							HttpStatus.BAD_REQUEST);
				}
			}
			admin.setIme(noviAdministrator.getIme());
			admin.setPrezime(noviAdministrator.getPrezime());
			admin.setKorisnickoIme(noviAdministrator.getKorisnickoIme());
			admin.setEmail(noviAdministrator.getEmail());
			admin.setLozinka(Encryption.getPassEncoded(noviAdministrator.getLozinka()));
			admin.setUloga(EKorisnikUloga.ROLE_ADMINISTRATOR);

			adminRepository.save(admin);

			return new ResponseEntity<Administrator>(admin, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniAdministratora(@PathVariable String id,
			@Valid @RequestBody AdministratorIzmenjenDTO izmenjenAdministrator, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!adminRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Administrator sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Administrator admin = adminRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenAdministrator.getIme() != null || !izmenjenAdministrator.getIme().equals(" ")
					|| !izmenjenAdministrator.getIme().equals("")) {
				admin.setIme(izmenjenAdministrator.getIme());
			}
			if (izmenjenAdministrator.getPrezime() != null || !izmenjenAdministrator.getPrezime().equals(" ")
					|| !izmenjenAdministrator.getPrezime().equals("")) {
				admin.setPrezime(izmenjenAdministrator.getPrezime());
			}
			if (izmenjenAdministrator.getKorisnickoIme() != null
					|| !izmenjenAdministrator.getKorisnickoIme().equals(" ")
					|| !izmenjenAdministrator.getKorisnickoIme().equals("")) {
				admin.setKorisnickoIme(izmenjenAdministrator.getKorisnickoIme());
			}
			if (izmenjenAdministrator.getEmail() != null || !izmenjenAdministrator.getEmail().equals(" ")
					|| !izmenjenAdministrator.getEmail().equals("")) {
				admin.setEmail(izmenjenAdministrator.getEmail());
			}

			adminRepository.save(admin);
			return new ResponseEntity<Administrator>(admin, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
