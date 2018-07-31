package com.example.e_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Predaje {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer idPredaje;

	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "nastavnik")
	protected Nastavnik nastavnik;

	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "predmet")
	protected Predmet predmet;

	@JsonBackReference
	// @JsonIgnore
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "predaje")
	protected List<PredajeUOdeljenju> predajeUOdeljenju = new ArrayList<>();

	@Version
	protected Integer version;

	public Predaje() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIdPredaje() {
		return idPredaje;
	}

	public void setIdPredaje(Integer idPredaje) {
		this.idPredaje = idPredaje;
	}

	public Nastavnik getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(Nastavnik nastavnik) {
		this.nastavnik = nastavnik;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public List<PredajeUOdeljenju> getPredajeUOdeljenju() {
		return predajeUOdeljenju;
	}

	public void setPredajeUOdeljenju(List<PredajeUOdeljenju> predajeUOdeljenju) {
		this.predajeUOdeljenju = predajeUOdeljenju;
	}

}
