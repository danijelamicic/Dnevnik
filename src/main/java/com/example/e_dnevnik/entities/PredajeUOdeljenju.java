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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PredajeUOdeljenju {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer idPredajeUOdeljenju;


	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "predaje")
	protected Predaje predaje;

	//@JsonIgnore
	//@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "odeljenje")
	protected Odeljenje odeljenje;

	@Version
	protected Integer version;

	@JsonBackReference
	@OneToMany(mappedBy = "predajeUOdeljenju", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<Ocena> ocene = new ArrayList<>();

	public PredajeUOdeljenju() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdPredajeUOdeljenju() {
		return idPredajeUOdeljenju;
	}

	public void setIdPredajeUOdeljenju(Integer idPredajeUOdeljenju) {
		this.idPredajeUOdeljenju = idPredajeUOdeljenju;
	}

	public Predaje getPredaje() {
		return predaje;
	}

	public void setPredaje(Predaje predaje) {
		this.predaje = predaje;
	}

	public Odeljenje getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(List<Ocena> ocene) {
		this.ocene = ocene;
	}

}
