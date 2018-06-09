package com.ponto_eletronico;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
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
	private Integer minutosTrabalhados;
	private Integer intervalo;
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

	public Integer getMinutosTrabalhados() {
		return minutosTrabalhados;
	}

	public void setMinutosTrabalhados(Integer minutosTrabalhados) {
		this.minutosTrabalhados = minutosTrabalhados;
	}

	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
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
		this.calcularJornada();
	}

	public void removeBatida(Batida batida) {
		batida.setJornada(null);
		this.batidas.remove(batida);
		this.calcularJornada();
	}
	
	private void calcularJornada() {
		minutosTrabalhados = null;
		intervalo = null;
		intervaloEstaCorreto = true;
		
		if (batidas.size() > 1) {
			batidas.sort((Batida batida1, Batida batida2) -> batida1.getHora().compareTo(batida2.getHora()));
			Batida impar = batidas.get(0);
			Batida par = null;
			for (int i = 1; i<batidas.size(); i++) {
				if (i % 2 == 1) {
					par = batidas.get(i);
					somarHorasTrabalhadas(impar, par);
				} else {
					impar = batidas.get(i);
					calcularIntervalo(par, impar);
				}
			}
		}
	}
	
	private void somarHorasTrabalhadas(Batida impar, Batida par) {
		OffsetTime horaBatidaImpar = impar.getHora();
		OffsetTime horaBatidaPar = par.getHora();
		
		int horas = horaBatidaPar.compareTo(horaBatidaImpar);
		System.out.println(horas);
	}
	
	private void calcularIntervalo(Batida par, Batida impar) {
		
	}
}
