package com.example.e_dnevnik.entities.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RazredDTO {
	
	@NotNull(message = "Razred mora biti unet")
	@Min(value=1, message="Razred mora biti od 1-8")
	@Max(value=8, message="Razred mora biti od 1-8")
	protected Integer vrednost;

	public RazredDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getVrednost() {
		return vrednost;
	}

	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}

}

