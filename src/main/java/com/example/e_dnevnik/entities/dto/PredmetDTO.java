package com.example.e_dnevnik.entities.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PredmetDTO {
	
	@NotNull(message="Morate uneti naziv predmeta")
	@Size(min=2, max=40, message="Naziv predmeta mora imati izmedju {min} i {max} slova")
	protected String nazivPredmeta;

	public PredmetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNazivPredmeta() {
		return nazivPredmeta;
	}

	public void setNazivPredmeta(String nazivPredmeta) {
		this.nazivPredmeta = nazivPredmeta;
	}


}