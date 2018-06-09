package com.ponto_eletronico;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"dia", "usuario_pis"})})
public class Jornada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private LocalDate dia;
	private Double horasTrabalhadas;
	private Double intervalo;
	@NotNull
	private boolean intervaloEstaCorreto = true;
	@NotNull
	@ManyToOne
	@JoinColumn(name="usuario_pis")
	private Usuario usuario;
	@OneToMany(mappedBy = "jornada", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Batida> batidas;

	public Jornada() {
		batidas = new ArrayList<Batida>();
	}

	public Jornada(Usuario usuario, LocalDate dia, Batida batida) {
		this.usuario = usuario;
		this.dia = dia;
		batidas = new ArrayList<Batida>();
		this.addBatida(batida);
	}
	
	public Jornada(Usuario usuario, OffsetDateTime dateTime) {
		this.usuario = usuario;
		this.dia = dateTime.toLocalDate();
		
		batidas = new ArrayList<Batida>();
		Batida batida = new Batida(dateTime.toOffsetTime());
		this.addBatida(batida);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public Double getHorasTrabalhadas() {
		return horasTrabalhadas;
	}

	public void setHorasTrabalhadas(Double horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}

	public Double getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Double intervalo) {
		this.intervalo = intervalo;
	}

	public boolean isIntervaloEstaCorreto() {
		return intervaloEstaCorreto;
	}

	public void setIntervaloEstaCorreto(boolean intervaloEstaCorreto) {
		this.intervaloEstaCorreto = intervaloEstaCorreto;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Batida> getBatidas() {
		return batidas;
	}

	public void setBatidas(List<Batida> batidas) {
		this.batidas = batidas;
	}

	public void addBatida(Batida batida) {
		batida.setJornada(this);
		this.batidas.add(batida);
	}

	public void removeBatida(Batida batida) {
		batida.setJornada(null);
		this.batidas.remove(batida);
	}
}
