package com.example.e_dnevnik.entities.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OcenaIzmenjenaDTO {
	
	@NotNull(message = "Ocena mora biti uneta")
	@Min(value = 1, message = "Ocena mora biti od 1-5")
	@Max(value = 5, message = "Ocena mora biti od 1-5")
	protected Integer vrednost;


	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	protected Date datum;
	
	@NotNull(message = "Polugodiste mora biti uneto")
	@Min(value = 1, message = "Polugodiste mora biti od 1-2")
	@Max(value = 2, message = "Polugodiste mora biti od 1-2")
	protected Integer polugodiste;

	public OcenaIzmenjenaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getVrednost() {
		return vrednost;
	}

	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}


	public Integer getPolugodiste() {
		return polugodiste;
	}

	public void setPolugodiste(Integer polugodiste) {
		this.polugodiste = polugodiste;
	}

	public Date getDate() {
		return datum;
	}

	public void setDate(Date datum) {
		this.datum = datum;
	}



	

}
