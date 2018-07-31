package com.example.e_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.e_dnevnik.enumerations.EKorisnikUloga;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Ucenik extends Korisnik {

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "odeljenje")
	@JsonManagedReference

	protected Odeljenje odeljenje;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "roditelj")
	@JsonManagedReference
	protected Roditelj roditelj;

	@JsonBackReference
	@OneToMany(mappedBy = "ucenik", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<Ocena> ocene = new ArrayList<>();

	public Ucenik() {
		super();
		this.uloga = EKorisnikUloga.ROLE_UCENIK;
	}

	public Odeljenje getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}

	public Roditelj getRoditelj() {
		return roditelj;
	}

	public void setRoditelj(Roditelj roditelj) {
		this.roditelj = roditelj;
	}

	public List<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(List<Ocena> ocene) {
		this.ocene = ocene;
	}

}
