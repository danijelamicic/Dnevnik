package com.example.e_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e_dnevnik.repositories.KorisnikRepository;
@Service
public class UserServiceDAOImpl implements UserServiceDAO {
	
	@Autowired
	KorisnikRepository userRepository;
	
	@Override
	public Integer returnLoggedId(String email){
			return userRepository.findByEmail(email).getId();
	}

}
