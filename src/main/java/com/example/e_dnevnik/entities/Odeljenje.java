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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Odeljenje {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer idOdeljenje;

	@NotNull(message = "Morate uneti vrednost odeljenja")
	@Min(value = 1, message = "Vrednost odeljenja mora biti od 1 - 8")
	@Max(value = 8, message = "Vrednost odeljenja mora biti od 1 - 8")
	protected Integer vrednost;

	//@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "razred")
	
	protected Razred razred;

	@JsonBackReference
	@OneToMany(mappedBy = "odeljenje", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<Ucenik> ucenici = new ArrayList<>();

	@JsonBackReference
	@OneToMany(mappedBy = "odeljenje", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<PredajeUOdeljenju> predajeUOdeljenju = new ArrayList<>();

	@Version
	protected Integer version;

	public Odeljenje() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdOdeljenje() {
		return idOdeljenje;
	}

	public void setIdOdeljenje(Integer idOdeljenje) {
		this.idOdeljenje = idOdeljenje;
	}

	public Integer getVrednost() {
		return vrednost;
	}

	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}

	public Razred getRazred() {
		return razred;
	}

	public void setRazred(Razred razred) {
		this.razred = razred;
	}

	public List<Ucenik> getUcenici() {
		return ucenici;
	}

	public void setUcenici(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}

	public List<PredajeUOdeljenju> getPredajeUOdeljenju() {
		return predajeUOdeljenju;
	}

	public void setPredajeUOdeljenju(List<PredajeUOdeljenju> predajeUOdeljenju) {
		this.predajeUOdeljenju = predajeUOdeljenju;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}