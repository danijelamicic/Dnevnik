package com.example.e_dnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_dnevnik.repositories.KorisnikRepository;
import com.example.e_dnevnik.services.UserServiceDAOImpl;

@RestController
@RequestMapping("dnevnik/auth/")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

	@Autowired
	KorisnikRepository korisnikRepository;

	@Autowired
	UserServiceDAOImpl uService;

// kontrola pristupa	

	@PostMapping(value = "login")
	public ResponseEntity<Object> login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().iterator().next().getAuthority();
		String id = "" + uService.returnLoggedId(auth.getName());
		return new ResponseEntity<>("{\"role\":\"" + role + "\", \"id\":\"" + id + "\"}", HttpStatus.OK);
	}

}
