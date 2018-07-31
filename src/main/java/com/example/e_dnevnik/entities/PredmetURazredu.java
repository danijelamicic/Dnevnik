package com.example.e_dnevnik.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PredmetURazredu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer idPrRaz;

	@NotNull(message = "Nedeljni fond casova mora biti unet")
	@Min(value = 0, message = "Nedeljni fond casova mora biti od 0-5")
	@Max(value = 5, message = "Nedeljni fond casova mora biti od 0-5")
	protected Integer nedeljniFond;


	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "predmet")
	protected Predmet predmet;

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "razred")
	protected Razred razred;

	@Version
	protected Integer version;

	public PredmetURazredu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdPrRaz() {
		return idPrRaz;
	}

	public void setIdPrRaz(Integer idPrRaz) {
		this.idPrRaz = idPrRaz;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public Razred getRazred() {
		return razred;
	}

	public void setRazred(Razred razred) {
		this.razred = razred;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getNedeljniFond() {
		return nedeljniFond;
	}

	public void setNedeljniFond(Integer nedeljniFond) {
		this.nedeljniFond = nedeljniFond;
	}

}