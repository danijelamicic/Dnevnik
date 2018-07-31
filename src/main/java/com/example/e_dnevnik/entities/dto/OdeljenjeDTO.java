package com.example.e_dnevnik.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OdeljenjeDTO {
	
	@NotNull(message = "Morate uneti odeljenje")
	@Min(value = 1, message = "Vrednost odeljenja mora biti od 1 - 8")
	@Max(value = 8, message = "Vrednost odeljenja mora biti od 1 - 8")
	protected Integer vrednost;
	
	protected Integer idRazred;

	public OdeljenjeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getVrednost() {
		return vrednost;
	}

	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}

	public Integer getIdRazred() {
		return idRazred;
	}

	public void setIdRazred(Integer idRazred) {
		this.idRazred = idRazred;
	}


}
