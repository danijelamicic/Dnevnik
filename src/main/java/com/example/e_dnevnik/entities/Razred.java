package com.example.e_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Razred {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Integer idRazred;
	
	@NotNull(message = "Razred mora biti unet")
	@Min(value=1, message="Razred mora biti od 1-8")
	@Max(value=8, message="Razred mora biti od 1-8")
	protected Integer vrednost;
	
	@Version
	protected Integer version;
	
	@JsonBackReference
	@OneToMany(mappedBy = "razred", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<PredmetURazredu> predmetiURazredu = new ArrayList<>();
	
	@JsonIgnore
	//@JsonBackReference
	@OneToMany(mappedBy = "razred", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<Odeljenje> odeljenja = new ArrayList<>();
	
	/*@JsonIgnore
	//@JsonBackReference
	@OneToMany(mappedBy = "razred", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<Ucenik> ucenici = new ArrayList<>();*/

	public Razred() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return idRazred;
	}

	public void setId(Integer idRazred) {
		this.idRazred = idRazred;
	}

	public Integer getVrednost() {
		return vrednost;
	}

	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIdRazred() {
		return idRazred;
	}

	public void setIdRazred(Integer idRazred) {
		this.idRazred = idRazred;
	}

	public List<Odeljenje> getOdeljenja() {
		return odeljenja;
	}

	public void setOdeljenja(List<Odeljenje> odeljenja) {
		this.odeljenja = odeljenja;
	}

	/*public List<Ucenik> getUcenik() {
		return ucenici;
	}

	public void setUcenik(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}*/

	public List<PredmetURazredu> getPredmetiURazredu() {
		return predmetiURazredu;
	}

	public void setPredmetiURazredu(List<PredmetURazredu> predmetiURazredu) {
		this.predmetiURazredu = predmetiURazredu;
	}

	
}