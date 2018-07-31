package com.example.e_dnevnik.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.e_dnevnik.enumerations.EKorisnikUloga;




public class UcenikDTO {
	
	@NotNull(message = "Morate uneti ime")
	@Size(min = 2, max = 30, message = "Ime mora imati izmedju {min} i {max} slova")
	protected String ime;
	@NotNull(message = "Morate uneti prezime")
	@Size(min = 2, max = 30, message = "Prezime mora imati izmedju {min} i {max} slova")
	protected String prezime;
	@NotNull(message = "Morate uneti korisnicko ime")
	@Size(min = 5, max = 15, message = "Korisnicko ime mora imati izmedju {min} i {max} slova")
	protected String korisnickoIme;
	@NotNull(message = "Morate uneti email")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	protected String email;
	@NotNull(message = "Morate uneti lozinku")
	@Size(min = 3, max = 70, message = "Lozinka mora imati izmedju {min} i {max} slova")
	protected String lozinka;
	protected EKorisnikUloga uloga= EKorisnikUloga.ROLE_UCENIK;
	
	public UcenikDTO() {
		super();
		this.uloga=EKorisnikUloga.ROLE_UCENIK;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public EKorisnikUloga getUloga() {
		return uloga;
	}
	public void setUloga(EKorisnikUloga uloga) {
		this.uloga = uloga;
	}

}
