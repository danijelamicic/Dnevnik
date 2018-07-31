package com.example.e_dnevnik.controllers.util;

import com.example.e_dnevnik.security.View;
import com.fasterxml.jackson.annotation.JsonView;


public class RESTError {

	@JsonView(View.UcenikPogled.class)
	private String message;

	public RESTError(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}