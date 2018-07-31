package com.example.e_dnevnik.entities;


import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Administrator extends Korisnik {

	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}

}
