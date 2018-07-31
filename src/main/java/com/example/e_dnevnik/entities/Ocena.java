package com.example.e_dnevnik.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Ocena {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer idOcena;

	@NotNull(message = "Ocena mora biti uneta")
	@Min(value = 1, message = "Ocena mora biti od 1-5")
	@Max(value = 5, message = "Ocena mora biti od 1-5")
	protected Integer vrednost;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date datum;

	@NotNull(message = "Polugodiste mora biti uneto")
	@Min(value = 1, message = "Polugodiste mora biti od 1-2")
	@Max(value = 2, message = "Polugodiste mora biti od 1-2")
	protected Integer polugodiste;

	@Version
	protected Integer version;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "predajeUOdeljenju")
	protected PredajeUOdeljenju predajeUOdeljenju;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ucenik")
	@JsonManagedReference
	protected Ucenik ucenik;

	public Ocena() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getVrednost() {
		return vrednost;
	}

	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}

	public Integer getId() {
		return idOcena;
	}

	public void setId(Integer idOcena) {
		this.idOcena = idOcena;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIdOcena() {
		return idOcena;
	}

	public void setIdOcena(Integer idOcena) {
		this.idOcena = idOcena;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenik ucenik) {
		this.ucenik = ucenik;
	}

	public PredajeUOdeljenju getPredajeUOdeljenju() {
		return predajeUOdeljenju;
	}

	public void setPredajeUOdeljenju(PredajeUOdeljenju predajeUOdeljenju) {
		this.predajeUOdeljenju = predajeUOdeljenju;
	}

	public Integer getPolugodiste() {
		return polugodiste;
	}

	public void setPolugodiste(Integer polugodiste) {
		this.polugodiste = polugodiste;
	}

	public Date getDate() {
		return datum;
	}

	public void setDate(Date datum) {
		this.datum = datum;
	}

	

}