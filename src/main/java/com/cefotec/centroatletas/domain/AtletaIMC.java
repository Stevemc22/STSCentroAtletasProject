package com.cefotec.centroatletas.domain;

import javax.persistence.*;

@Entity
public class AtletaIMC {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;
	
	private double IMC;
	
	private String fecha;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Atleta atleta;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public double getIMC() {
		return IMC;
	}

	public void setIMC(double iMC) {
		IMC = iMC;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Atleta getAtleta() {
		return atleta;
	}

	public void setAtleta(Atleta atleta) {
		this.atleta = atleta;
	}
	
}
