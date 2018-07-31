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
import com.example.e_dnevnik.entities.Odeljenje;
import com.example.e_dnevnik.entities.PredajeUOdeljenju;
import com.example.e_dnevnik.entities.Roditelj;
import com.example.e_dnevnik.entities.Ucenik;
import com.example.e_dnevnik.entities.dto.OcenaPredmetDTO;
import com.example.e_dnevnik.entities.dto.UcenikDTO;
import com.example.e_dnevnik.entities.dto.UcenikIzmenjenDTO;
import com.example.e_dnevnik.enumerations.EKorisnikUloga;
import com.example.e_dnevnik.repositories.KorisnikRepository;
import com.example.e_dnevnik.repositories.OcenaRepository;
import com.example.e_dnevnik.repositories.OdeljenjeRepository;
import com.example.e_dnevnik.repositories.PredajeUOdeljenjuRepository;
import com.example.e_dnevnik.repositories.PredmetRepository;
import com.example.e_dnevnik.repositories.RazredRepository;
import com.example.e_dnevnik.repositories.RoditeljRepository;
import com.example.e_dnevnik.repositories.UcenikRepository;
import com.example.e_dnevnik.services.LozinkaDAOImpl;

@RestController
@RequestMapping(path = "/dnevnik/ucenici")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UcenikController {

	@Autowired
	UcenikRepository ucenikRepository;

	@Autowired
	PredmetRepository predmetRepository;;

	@Autowired
	KorisnikRepository korisnikRepository;

	@Autowired
	RoditeljRepository roditeljRepository;

	@Autowired
	OcenaRepository ocenaRepository;

	@Autowired
	RazredRepository razredRepository;

	@Autowired
	LozinkaDAOImpl lozinkaDAO;

	@Autowired
	OdeljenjeRepository odeljenjeRepository;

	@Autowired
	PredajeUOdeljenjuRepository predajeuodeljenjuRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}

// promena lozinke ucenika
	@Secured("ROLE_UCENIK")
	@RequestMapping(method = RequestMethod.PUT, value = "/change-password/{id}")
	public ResponseEntity<?> promenaLozinke(@PathVariable String id, @RequestParam String staraLozinka,
			@RequestParam String novaLozinka) {

		return lozinkaDAO.promenaLozinke(id, staraLozinka, novaLozinka);

	}

// vrati sve ucenike
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSveUcenike() {
		return new ResponseEntity<Iterable<Ucenik>>(ucenikRepository.findAll(), HttpStatus.OK);
	}

// dodavanje novog ucenika
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNovogUcenika(@Valid @RequestBody UcenikDTO noviUcenik, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			// provera autentičnosti email-a
			Ucenik ucenik = new Ucenik();
			for (Korisnik korisnik : korisnikRepository.findAll()) {
				if (korisnik.getEmail().equals(noviUcenik.getEmail())) {
					return new ResponseEntity<RESTError>(
							new RESTError("Već postoji registrovan korisnik sa prosledjenim email-om !"),
							HttpStatus.BAD_REQUEST);
				}
			}
			ucenik.setIme(noviUcenik.getIme());
			ucenik.setPrezime(noviUcenik.getPrezime());
			ucenik.setKorisnickoIme(noviUcenik.getKorisnickoIme());
			ucenik.setEmail(noviUcenik.getEmail());
			ucenik.setLozinka(Encryption.getPassEncoded(noviUcenik.getLozinka()));
			ucenik.setUloga(EKorisnikUloga.ROLE_UCENIK);
			ucenikRepository.save(ucenik);

			return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

// izmena podataka o uceniku
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniUcenika(@PathVariable String id,
			@Valid @RequestBody UcenikIzmenjenDTO izmenjenUcenik, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenUcenik.getIme() != null || !izmenjenUcenik.getIme().equals(" ")
					|| !izmenjenUcenik.getIme().equals("")) {
				ucenik.setIme(izmenjenUcenik.getIme());
			}
			if (izmenjenUcenik.getPrezime() != null || !izmenjenUcenik.getPrezime().equals(" ")
					|| !izmenjenUcenik.getPrezime().equals("")) {
				ucenik.setPrezime(izmenjenUcenik.getPrezime());
			}
			if (izmenjenUcenik.getKorisnickoIme() != null || !izmenjenUcenik.getKorisnickoIme().equals(" ")
					|| !izmenjenUcenik.getKorisnickoIme().equals("")) {
				ucenik.setKorisnickoIme(izmenjenUcenik.getKorisnickoIme());
			}
			if (izmenjenUcenik.getEmail() != null || !izmenjenUcenik.getEmail().equals(" ")
					|| !izmenjenUcenik.getEmail().equals("")) {
				ucenik.setEmail(izmenjenUcenik.getEmail());
			}

			ucenikRepository.save(ucenik);
			return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

//dodavanje ucenika u odeljenje
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/dodajodeljenje")
	public ResponseEntity<?> dodajUcenikuOdeljenje(@PathVariable String id, @RequestParam Integer idOdeljenje) {
		try {

			if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();

			if (!odeljenjeRepository.findById((idOdeljenje)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Odeljenje sa prosledjenim id nije pronadjeno"),
						HttpStatus.BAD_REQUEST);
			}
			Odeljenje odeljenje = odeljenjeRepository.findById((idOdeljenje)).get();
			ucenik.setOdeljenje(odeljenje);

			ucenikRepository.save(ucenik);
			return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

//dodavanje ucenika roditelju
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.PUT, value = "/dodajroditelja/{id}")
	public ResponseEntity<?> dodajUcenikuRoditelja(@PathVariable String id, @RequestParam Integer idRoditelj) {
		try {

			if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();

			Roditelj roditelj = roditeljRepository.findById((idRoditelj)).get();
			ucenik.setRoditelj(roditelj);

			ucenikRepository.save(ucenik);
			return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

// traženje učenika po  Id
	@Secured({ "ROLE_RODITELJ", "ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK" })
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiUcenikaPoId(@PathVariable String id) {
		try {
			if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

// sve ocene jednog ucenika po svim predmetima
	@Secured({ "ROLE_UCENIK", "ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK" })
	@RequestMapping(method = RequestMethod.GET, value = "/ocenapredmet/{idUcenik}")
	public ResponseEntity<?> pronadjiOceneUcenikaPoPredmetu(@PathVariable String idUcenik) {

		if (!ucenikRepository.findById(Integer.parseInt(idUcenik)).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosleđenim id nije pronađen"),
					HttpStatus.NOT_FOUND);
		}

		Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(idUcenik)).get();

		Odeljenje odeljenje = new Odeljenje();
		List<OcenaPredmetDTO> ocenapredmet = new ArrayList<>();

		odeljenje = ucenik.getOdeljenje();

		if (odeljenje == null) {
			return new ResponseEntity<RESTError>(new RESTError("Ucenik ne pripada ni jednom odeljenju"),
					HttpStatus.NOT_FOUND);
		}

		List<PredajeUOdeljenju> predajeuodeljenju = (predajeuodeljenjuRepository.findByOdeljenje(odeljenje));

		// prolazak kroz listu nastavnika koji predaju u odeljenju i dodela vrednosti
		// predmeta i liste ocena za svaki
		// predmet u namenski DTO objekat koji se sastoji od predmeta i njihovih ocena

		for (PredajeUOdeljenju p : predajeuodeljenju) {
			OcenaPredmetDTO temp = new OcenaPredmetDTO();
			temp.setPredmet(p.getPredaje().getPredmet());
			temp.setOcena(ocenaRepository.findByUcenikAndPredajeUOdeljenju_Predaje_Predmet(ucenik, temp.getPredmet()));
			ocenapredmet.add(temp);
		}

		return new ResponseEntity<List<OcenaPredmetDTO>>(ocenapredmet, HttpStatus.OK);
	}

// brisanje ucenika po Id
	@Secured("ROLE_ADMINISTRATOR")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiUcenikaPoId(@PathVariable String id) {
		try {
			if (!ucenikRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(id)).get();

			if (ucenik.getOdeljenje() != null) {
				return new ResponseEntity<RESTError>(
						new RESTError("Nije dozvoljeno brisanje ucenika sa prosledjenim id"), HttpStatus.BAD_REQUEST);
			}

			ucenikRepository.delete(ucenik);
			return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
