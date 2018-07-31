package com.example.e_dnevnik.entities.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.e_dnevnik.entities.Ocena;
import com.example.e_dnevnik.entities.Predmet;

public class OcenaPredmetDTO {
	
	
	protected Predmet predmet;
	protected List<Ocena> ocena = new ArrayList<>();
	
	public OcenaPredmetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<Ocena> getOcena() {
		return ocena;
	}
	public void setOcena(List<Ocena> ocena) {
		this.ocena = ocena;
	}
	public Predmet getPredmet() {
		return predmet;
	}
	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

}
