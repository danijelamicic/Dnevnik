package com.example.e_dnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Roditelj extends Korisnik {

	@OneToMany(mappedBy = "roditelj", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonBackReference
	protected List<Ucenik> ucenici = new ArrayList<>();

	public Roditelj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Ucenik> getUcenici() {
		return ucenici;
	}

	public void setUcenici(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}

}