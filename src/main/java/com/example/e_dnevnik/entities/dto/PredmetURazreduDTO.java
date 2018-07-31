package com.example.e_dnevnik.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PredmetURazreduDTO {
	
	@NotNull(message = "Nedeljni fond casova mora biti unet")
	@Min(value = 1, message = "Nedeljni fond casova mora biti od 1-5")
	@Max(value = 5, message = "Nedeljni fond casova mora biti od 1-5")
	protected Integer nedeljniFond;

	protected Integer idPredmet;

	protected Integer idRazred;

	public PredmetURazreduDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getNedeljniFond() {
		return nedeljniFond;
	}

	public void setNedeljniFond(Integer nedeljniFond) {
		this.nedeljniFond = nedeljniFond;
	}

	public Integer getIdPredmet() {
		return idPredmet;
	}

	public void setIdPredmet(Integer idPredmet) {
		this.idPredmet = idPredmet;
	}

	public Integer getIdRazred() {
		return idRazred;
	}

	public void setIdRazred(Integer idRazred) {
		this.idRazred = idRazred;
	}

}
