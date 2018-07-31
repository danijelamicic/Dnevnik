package com.example.e_dnevnik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_dnevnik.models.EmailObject;
import com.example.e_dnevnik.services.EmailService;



@RestController
@RequestMapping(path = "/email")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class EmailController {

	@Autowired
	EmailService emailService;

	@RequestMapping(method = RequestMethod.POST, value = "/simpleEmail")
	public String sendSimpleMessage(@RequestBody EmailObject object) {
		if (object == null || object.getTo() == null || object.getText() == null) {
			return null;
		}
		emailService.sendSimpleMessage(object);
		return "Uspešno ste poslali elektronsku poštu !";
	}

}