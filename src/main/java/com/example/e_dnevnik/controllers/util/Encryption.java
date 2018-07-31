package com.example.e_dnevnik.controllers.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

	public static String getPassEncoded(String pass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(pass);
	}

	public static void main(String[] args) {
		System.out.println(getPassEncoded("pass"));
	}

	public static boolean matchPass(String pass, String encPass) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(pass, encPass);
	}

}
