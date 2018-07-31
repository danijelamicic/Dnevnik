package com.example.e_dnevnik.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.e_dnevnik.controllers.util.RESTError;
import com.example.e_dnevnik.entities.Ocena;
import com.example.e_dnevnik.entities.Odeljenje;
import com.example.e_dnevnik.entities.PredajeUOdeljenju;
import com.example.e_dnevnik.entities.Ucenik;
import com.example.e_dnevnik.entities.dto.OcenaDTO;
import com.example.e_dnevnik.entities.dto.OcenaIzmenjenaDTO;
import com.example.e_dnevnik.entities.dto.OcenaPredmetDTO;
import com.example.e_dnevnik.models.EmailObject;
import com.example.e_dnevnik.repositories.OcenaRepository;
import com.example.e_dnevnik.repositories.OdeljenjeRepository;
import com.example.e_dnevnik.repositories.PredajeUOdeljenjuRepository;
import com.example.e_dnevnik.repositories.UcenikRepository;
import com.example.e_dnevnik.services.EmailService;

@RestController
@RequestMapping(path = "/dnevnik/ocene")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class OcenaController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	OcenaRepository ocenaRepository;

	@Autowired
	UcenikRepository ucenikRepository;

	@Autowired
	PredajeUOdeljenjuRepository predajeUOdeljenjuRepository;

	@Autowired
	OdeljenjeRepository odeljenjRepository;

	@Autowired
	EmailService posaljiMail;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(" "));
	}

	@Secured({ "ROLE_ADMINISTRATOR" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> pronadjiSveOcene() {
		return new ResponseEntity<Iterable<Ocena>>(ocenaRepository.findAll(), HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> dodajNovuOcenu(@Valid @RequestBody OcenaDTO novaOcena, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			logger.debug("This is a debug message");
			logger.info("This is an info message");
			logger.warn("This is a warn message");
			logger.error("This is an error message");
			if (novaOcena.getIdPredajeUOdeljenju() == null || novaOcena.getIdUcenik() == null) {
				return new ResponseEntity<RESTError>(
						new RESTError(
								"Objekat ocena nije ispravan, morate uneti vrednosti id za PredajeUOdeljenju i Ucenik"),
						HttpStatus.BAD_REQUEST);
			}

			if (!predajeUOdeljenjuRepository.findById(novaOcena.getIdPredajeUOdeljenju()).isPresent()) {
				return new ResponseEntity<RESTError>(
						new RESTError("PredajeUOdeljenju sa prosledjenim id nije pronadjen"), HttpStatus.NOT_FOUND);
			}
			PredajeUOdeljenju predajeUOdeljenju = predajeUOdeljenjuRepository
					.findById(novaOcena.getIdPredajeUOdeljenju()).get();

			if (!ucenikRepository.findById(novaOcena.getIdUcenik()).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjen"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(novaOcena.getIdUcenik()).get();

			if (!ucenik.getOdeljenje().equals(predajeUOdeljenju.getOdeljenje())) {
				return new ResponseEntity<RESTError>(
						new RESTError("Ocenu može dati samo nastavnik koji predaje učeniku"), HttpStatus.BAD_REQUEST);
			}

			Ocena ocena = new Ocena();

			ocena.setVrednost(novaOcena.getVrednost());
			ocena.setDate(new Date());
			ocena.setPolugodiste(novaOcena.getPolugodiste());
			ocena.setPredajeUOdeljenju(predajeUOdeljenju);
			ocena.setUcenik(ucenik);

			ocenaRepository.save(ocena);
			EmailObject mail = new EmailObject(ucenik.getRoditelj().getEmail(), "nova ocena", text(ocena));
			posaljiMail.sendSimpleMessage(mail);

			return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_NASTAVNIK" })
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> izmeniOcenu(@PathVariable String id, @Valid @RequestBody OcenaIzmenjenaDTO izmenjenaOcena,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<String>(this.createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			if (!ocenaRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ocena sa prosledjenim id nije pronadjena"),
						HttpStatus.NOT_FOUND);
			}

			Ocena ocena = ocenaRepository.findById(Integer.parseInt(id)).get();

			if (izmenjenaOcena.getVrednost() != null || !izmenjenaOcena.getVrednost().toString().equals(" ")
					|| !izmenjenaOcena.getVrednost().toString().equals("")) {
				ocena.setVrednost(izmenjenaOcena.getVrednost());
			}
			if (izmenjenaOcena.getPolugodiste() != null || !izmenjenaOcena.getPolugodiste().toString().equals(" ")
					|| !izmenjenaOcena.getPolugodiste().toString().equals("")) {
				ocena.setPolugodiste(izmenjenaOcena.getPolugodiste());
			}
		
			ocena.setDate(new Date());
			
			Ucenik ucenik = new Ucenik();
			ucenik = ocena.getUcenik();

			PredajeUOdeljenju predajeuodeljenju = new PredajeUOdeljenju();
			predajeuodeljenju = ocena.getPredajeUOdeljenju();

			if (!ucenik.getOdeljenje().equals(predajeuodeljenju.getOdeljenje())) {
				return new ResponseEntity<RESTError>(
						new RESTError("Ocenu može izmeniti samo nastavnik koji predaje učeniku"),
						HttpStatus.BAD_REQUEST);
			}
			ocenaRepository.save(ocena);

			EmailObject mail = new EmailObject(ucenik.getRoditelj().getEmail(), "nova ocena", textIzmena(ocena));
			posaljiMail.sendSimpleMessage(mail);

			return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured({ "ROLE_ADMINISTRATOR",  "ROLE_NASTAVNIK" })
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> pronadjiOcenuPoId(@PathVariable String id) {
		try {
			if (!ocenaRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ocena sa prosledjenim id nije pronadjena"),
						HttpStatus.NOT_FOUND);
			}
			Ocena ocena = ocenaRepository.findById(Integer.parseInt(id)).get();

			return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMINISTRATOR" })
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> obrisiOcenuPoId(@PathVariable String id) {
		try {
			if (!ocenaRepository.findById(Integer.parseInt(id)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ocena sa prosledjenim id nije pronadjena"),
						HttpStatus.NOT_FOUND);
			}
			Ocena ocena = ocenaRepository.findById(Integer.parseInt(id)).get();

			if (ocena.getUcenik() != null || ocena.getPredajeUOdeljenju() != null) {
				return new ResponseEntity<RESTError>(new RESTError("Brisanje ocene sa prosledjenim id nije dozvoljeno"),
						HttpStatus.BAD_REQUEST);
			}

			ocenaRepository.delete(ocena);
			return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ" })
	@RequestMapping(method = RequestMethod.GET, value = "/{idUcenika}/ucenikPredmet/{idPredajeUOdeljenju}")
	public ResponseEntity<?> prikaziSveOceneUcenikaPoPredmetu(@PathVariable String idUcenika,
			@PathVariable String idPredajeUOdeljenju) {
		try {
			if (!ucenikRepository.findById(Integer.parseInt(idUcenika)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjena"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(idUcenika)).get();

			if (!predajeUOdeljenjuRepository.findById(Integer.parseInt(idPredajeUOdeljenju)).isPresent()) {
				return new ResponseEntity<RESTError>(
						new RESTError("PredajeUOdeljenju sa prosledjenim id nije pronadjen"), HttpStatus.NOT_FOUND);
			}
			PredajeUOdeljenju nastavnikPredmet = predajeUOdeljenjuRepository
					.findById(Integer.parseInt(idPredajeUOdeljenju)).get();

			List<Ocena> ocene = ocenaRepository.findByPredajeUOdeljenjuAndUcenik(nastavnikPredmet, ucenik);
			ocenaRepository.saveAll(ocene);

			return new ResponseEntity<List<Ocena>>(ocene, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ" })
	@RequestMapping(method = RequestMethod.GET, value = "/prosek/{idUcenika}")
	public ResponseEntity<?> prosecnaOcenaPoPredmetu(@PathVariable String idUcenika,
			@RequestParam String idPredajeUOdeljenju) {
		try {
			if (!ucenikRepository.findById(Integer.parseInt(idUcenika)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjena"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(idUcenika)).get();

			if (!predajeUOdeljenjuRepository.findById(Integer.parseInt(idPredajeUOdeljenju)).isPresent()) {
				return new ResponseEntity<RESTError>(
						new RESTError("PredajeUOdeljenju sa prosledjenim id nije pronadjen"), HttpStatus.NOT_FOUND);
			}
			PredajeUOdeljenju nastavnikPredmet = predajeUOdeljenjuRepository
					.findById(Integer.parseInt(idPredajeUOdeljenju)).get();

			List<Ocena> ocene = ocenaRepository.findByPredajeUOdeljenjuAndUcenik(nastavnikPredmet, ucenik);

			Double zbirOcena = 0.00;
			int i = 0;

			for (Ocena o : ocene) {

				zbirOcena += o.getVrednost();

				i++;

			}
			Double prosecnaOcena = (zbirOcena / i);

			ocenaRepository.saveAll(ocene);

			return new ResponseEntity<Double>(prosecnaOcena, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_NASTAVNIK", "ROLE_RODITELJ" })
	@RequestMapping(method = RequestMethod.GET, value = "/zakljucna/{idUcenika}")
	public ResponseEntity<?> zakljucnaOcenaPoPredmetu(@PathVariable String idUcenika,
			@RequestParam String idPredajeUOdeljenju) {
		try {
			if (!ucenikRepository.findById(Integer.parseInt(idUcenika)).isPresent()) {
				return new ResponseEntity<RESTError>(new RESTError("Ucenik sa prosledjenim id nije pronadjena"),
						HttpStatus.NOT_FOUND);
			}
			Ucenik ucenik = ucenikRepository.findById(Integer.parseInt(idUcenika)).get();

			if (!predajeUOdeljenjuRepository.findById(Integer.parseInt(idPredajeUOdeljenju)).isPresent()) {
				return new ResponseEntity<RESTError>(
						new RESTError("PredajeUOdeljenju sa prosledjenim id nije pronadjen"), HttpStatus.NOT_FOUND);
			}
			PredajeUOdeljenju nastavnikPredmet = predajeUOdeljenjuRepository
					.findById(Integer.parseInt(idPredajeUOdeljenju)).get();

			List<Ocena> ocene = ocenaRepository.findByPredajeUOdeljenjuAndUcenik(nastavnikPredmet, ucenik);

			Float zbirOcena = (float) 0.00;
			int i = 0;

			for (Ocena o : ocene) {

				zbirOcena += o.getVrednost();

				i++;

			}
			Integer zakljucnaOcena = Math.round((zbirOcena / i));

			ocenaRepository.saveAll(ocene);

			return new ResponseEntity<Integer>(zakljucnaOcena, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Dogodila se greška"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_UCENIK", "ROLE_RODITELJ" })
	@RequestMapping(method = RequestMethod.GET, value = "/student/{ucenikId}")
	public ResponseEntity<?> oceneUcenikaPoSvimPredmetima(@Valid @PathVariable String ucenikId) {
		Odeljenje odeljenje = new Odeljenje();
		List<OcenaPredmetDTO> ocenapredmet = new ArrayList<>();
		OcenaPredmetDTO temp = new OcenaPredmetDTO();
		odeljenje = ucenikRepository.findById(Integer.parseInt(ucenikId)).get().getOdeljenje();

		for (PredajeUOdeljenju predajeUOdeljenju : (predajeUOdeljenjuRepository.findByOdeljenje(odeljenje))) {
			temp.setPredmet(predajeUOdeljenju.getPredaje().getPredmet());
			temp.setOcena(ocenaRepository.findByUcenik_Odeljenje_PredajeUOdeljenju_Predaje_Predmet(temp.getPredmet()));
			ocenapredmet.add(temp);
		}

		return new ResponseEntity<List<OcenaPredmetDTO>>(ocenapredmet, HttpStatus.OK);
	}

	private String text(Ocena ocena) {
		return "Poštovani, učenik " + ocena.getUcenik().getIme() + " " + ocena.getUcenik().getPrezime()
				+ " dobio je ocenu " + ocena.getVrednost() + " iz predmeta "
				+ ocena.getPredajeUOdeljenju().getPredaje().getPredmet().getNazivPredmeta() + " kod nastavnika "
				+ ocena.getPredajeUOdeljenju().getPredaje().getNastavnik().getIme() + " "
				+ ocena.getPredajeUOdeljenju().getPredaje().getNastavnik().getPrezime() + ".";
	}

	private String textIzmena(Ocena ocena) {
		return "Poštovani, učeniku " + ocena.getUcenik().getIme() + " " + ocena.getUcenik().getPrezime()
				+ " izmenjena je " + ocena.getVrednost() + " iz predmeta "
				+ ocena.getPredajeUOdeljenju().getPredaje().getPredmet().getNazivPredmeta() + " kod nastavnika "
				+ ocena.getPredajeUOdeljenju().getPredaje().getNastavnik().getIme() + " "
				+ ocena.getPredajeUOdeljenju().getPredaje().getNastavnik().getPrezime() + ".";
	}

}