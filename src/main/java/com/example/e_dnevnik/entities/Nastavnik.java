package com.example.e_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Nastavnik extends Korisnik {

	@NotNull(message = "Morate uneti zvanje nastavnika")
	@Size(min = 7, max = 8, message = "Zvanje nastavnika mora imati izmedju {min} i {max} slova")
	protected String zvanje;

	@JsonBackReference
	@OneToMany(mappedBy = "nastavnik", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	protected List<Predaje> predaje = new ArrayList<>();

	public Nastavnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getZvanje() {
		return zvanje;
	}

	public void setZvanje(String zvanje) {
		this.zvanje = zvanje;
	}

	public List<Predaje> getPredaje() {
		return predaje;
	}

	public void setPredaje(List<Predaje> predaje) {
		this.predaje = predaje;
	}

}