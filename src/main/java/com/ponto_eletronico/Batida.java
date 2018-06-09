package com.ponto_eletronico;

import java.time.OffsetTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"hora", "jornada_id"})})
public class Batida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull
	@ManyToOne
	@JoinColumn(name="jornada_id")
	@JsonIgnore
	private Jornada jornada;
	@NotNull
	private OffsetTime hora;
	
	public Batida() {
		// TODO Auto-generated constructor stub
	}
	
	public Batida(OffsetTime hora) {
		this.setHora(hora);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Jornada getJornada() {
		return jornada;
	}

	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}

	public OffsetTime getHora() {
		return hora;
	}

	public void setHora(OffsetTime hora) {
		this.hora = hora.withSecond(0);
	}
	
}
