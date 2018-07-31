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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Predmet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer idPredmet;

	@NotNull(message = "Morate uneti naziv predmeta")
	@Size(min = 2, max = 40, message = "Naziv predmeta mora imati izmedju {min} i {max} slova")
	protected String nazivPredmeta;

	@JsonBackReference
	@OneToMany(mappedBy = "predmet", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<Predaje> predaje = new ArrayList<>();

	@JsonBackReference
	@OneToMany(mappedBy = "predmet", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<PredmetURazredu> predmetiURazredu = new ArrayList<>();

	@Version
	protected Integer version;

	public Predmet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return idPredmet;
	}

	public void setId(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}

	public String getNazivPredmeta() {
		return nazivPredmeta;
	}

	public void setNazivPredmeta(String nazivPredmeta) {
		this.nazivPredmeta = nazivPredmeta;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIdPredmet() {
		return idPredmet;
	}

	public void setIdPredmet(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}

	public List<Predaje> getPredaje() {
		return predaje;
	}

	public void setPredaje(List<Predaje> predaje) {
		this.predaje = predaje;
	}

	public List<PredmetURazredu> getPredmetiURazredu() {
		return predmetiURazredu;
	}

	public void setPredmetiURazredu(List<PredmetURazredu> predmetiURazredu) {
		this.predmetiURazredu = predmetiURazredu;
	}

}
