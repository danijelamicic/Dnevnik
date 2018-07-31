package com.example.e_dnevnik.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.e_dnevnik.enumerations.EKorisnikUloga;
import com.example.e_dnevnik.security.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;


@Inheritance(strategy=InheritanceType.JOINED)
@Entity
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonView(View.AdministratorPogled.class)
	protected Integer id;
	
	@NotNull(message= "Morate uneti ime")
	@Size(min=2, max=30, message="Ime mora imati izmedju {min} i {max} slova")
	@JsonView(View.UcenikPogled.class)
	protected String ime;
	
	@NotNull(message= "Morate uneti prezime")
	@Size(min=2, max=30, message="Prezime mora imati izmedju {min} i {max} slova")
	@JsonView(View.UcenikPogled.class)
	protected String prezime;
	
	@NotNull(message= "Morate uneti korisnicko ime")
	@Size(min=5, max=15, message="Korisnicko ime mora imati izmedju {min} i {max} slova")
	@JsonView(View.UcenikPogled.class)
	protected String korisnickoIme;
	
	@NotNull(message = "Morate uneti email")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@JsonView(View.AdministratorPogled.class)
	protected String email;
	
	@JsonIgnore
	@NotNull(message= "Morate uneti lozinku")
	@Size(min=3, max=100, message="Lozinka mora imati izmedju {min} i {max} slova")
	@JsonView(View.AdministratorPogled.class)
	protected String lozinka;
	
	@Enumerated(EnumType.STRING)
	protected EKorisnikUloga uloga;
	
	@Version
	protected Integer version;
	
	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public EKorisnikUloga getUloga() {
		return uloga;
	}

	public void setUloga(EKorisnikUloga uloga) {
		this.uloga = uloga;
	}
	
	
}