package com.ponto_eletronico;

import java.time.DayOfWeek;
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

	private static final int TEMPO_SEM_INTERVALO = 240;
	private static final int TEMPO_INTERVALO_MAIOR = 360;
	private static final int INTERVALO_MENOR = 15;
	private static final int INTERVALO_MAIOR = 60;
	private static final double FATOR_DOMINGO = 2;
	private static final double FATOR_SABADO = 1.5;
	private static final double FATOR_NOTURNO = 1.2;
	private static final int LIMITE_NOTURNO_MANHA = 360; 
	private static final int LIMITE_NOTURNO_NOITE = 1320; 
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private LocalDate dia;
	private int minutosTrabalhados;
	private int minutosTrabalhadosCorridos;
	private int intervalo;
	@NotNull
	private boolean intervaloEstaCorreto = true;
	@NotNull
	@ManyToOne
	@JoinColumn(name="usuario_pis")
	private Usuario usuario;
	@OneToMany(mappedBy = "jornada", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Batida> batidas;

	public Jornada() {
		this.batidas = new ArrayList<Batida>();
	}

	public Jornada(Usuario usuario, LocalDate dia, Batida batida) {
		this.usuario = usuario;
		this.dia = dia;
		this.batidas = new ArrayList<Batida>();
		this.addBatida(batida);
	}
	
	public Jornada(Usuario usuario, OffsetDateTime dateTime) {
		this.usuario = usuario;
		this.dia = dateTime.toLocalDate();
		
		this.batidas = new ArrayList<Batida>();
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

	public int getMinutosTrabalhados() {
		return minutosTrabalhados;
	}

	public void setMinutosTrabalhados(int minutosTrabalhados) {
		this.minutosTrabalhados = minutosTrabalhados;
	}

	public int getMinutosTrabalhadosCorridos() {
		return minutosTrabalhadosCorridos;
	}

	public void setMinutosTrabalhadosCorridos(int minutosTrabalhadosCorridos) {
		this.minutosTrabalhadosCorridos = minutosTrabalhadosCorridos;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
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
		this.minutosTrabalhados = 0;
		this.minutosTrabalhadosCorridos = 0;
		this.intervalo = 0;
		this.intervaloEstaCorreto = true;
		
		if (this.batidas.size() > 1) {
			this.batidas.sort((Batida batida1, Batida batida2) -> batida1.getHora().compareTo(batida2.getHora()));
			Batida impar = this.batidas.get(0);
			Batida par = null;
			for (int i = 1; i < this.batidas.size(); i++) {
				if (i % 2 == 1) {
					par = this.batidas.get(i);
					this.somarHorasTrabalhadas(impar, par);
					this.calcularIntervaloValido();
				} else {
					impar = this.batidas.get(i);
					this.calcularIntervalo(par, impar);
				}
			}
		}
	}
	
	private void somarHorasTrabalhadas(Batida impar, Batida par) {
		OffsetTime horaBatidaImpar = impar.getHora();
		OffsetTime horaBatidaPar = par.getHora();
		
		int tempoMinutosImpar = this.getTempoEmMinutos(horaBatidaImpar);
		int tempoMinutosPar = this.getTempoEmMinutos(horaBatidaPar);
		
		int minutosAtual = tempoMinutosPar - tempoMinutosImpar;
		int minutosNoturno = this.getTempoNoturno(tempoMinutosImpar, tempoMinutosPar);
		
		this.minutosTrabalhadosCorridos += minutosAtual;
		
		minutosAtual = minutosAtual - minutosNoturno;
		
		minutosNoturno =  (int) (minutosNoturno * FATOR_NOTURNO);
		minutosAtual += minutosNoturno;
		
		DayOfWeek dayOfWeek = dia.getDayOfWeek();
		if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
			minutosAtual = (int) (minutosAtual * FATOR_SABADO);
		} else if(dayOfWeek.equals(DayOfWeek.SUNDAY)) {
			minutosAtual = (int) (minutosAtual * FATOR_DOMINGO);
		}
		this.minutosTrabalhados += minutosAtual;
	}
	
	private void calcularIntervalo(Batida par, Batida impar) {
		OffsetTime horaBatidaPar = par.getHora();
		OffsetTime horaBatidaImpar = impar.getHora();
		
		int tempoMinutosPar = getTempoEmMinutos(horaBatidaPar);
		int tempoMinutosImpar = getTempoEmMinutos(horaBatidaImpar);
		
		int intervaloAtual = tempoMinutosImpar - tempoMinutosPar;
		
		if (this.intervalo < intervaloAtual) {
			this.intervalo = intervaloAtual;
			this.calcularIntervaloValido();
		}
	}
	
	private void calcularIntervaloValido() {
		if (this.minutosTrabalhadosCorridos <= TEMPO_SEM_INTERVALO) {
			this.intervaloEstaCorreto = true;
		} else if (this.minutosTrabalhadosCorridos > TEMPO_INTERVALO_MAIOR) {
			if (this.intervalo > INTERVALO_MAIOR) {
				this.intervaloEstaCorreto = true;
			} else {
				this.intervaloEstaCorreto = false;
			}
		} else {
			if (this.intervalo >= INTERVALO_MENOR) {
				this.intervaloEstaCorreto = true;
			} else {
				this.intervaloEstaCorreto = false;
			}
		}
	}
	
	private int getTempoEmMinutos(OffsetTime time) {
		int horas = time.getHour();
		int minutos = time.getMinute();
		return (horas * 60) + minutos;
	}
	
	private int getTempoNoturno(int tempoImpar, int tempoPar) {
		int tempoNoturno = 0;
		
		if (tempoImpar < LIMITE_NOTURNO_MANHA) {
			if (tempoPar < LIMITE_NOTURNO_MANHA) {
				tempoNoturno = tempoPar - tempoImpar;
			} else {
				tempoNoturno = LIMITE_NOTURNO_MANHA - tempoImpar;
			}
		}
		if (tempoPar > LIMITE_NOTURNO_NOITE) {
			if (tempoImpar > LIMITE_NOTURNO_NOITE) {
				tempoNoturno += tempoPar - tempoImpar;
			} else {
				tempoNoturno += tempoPar - LIMITE_NOTURNO_NOITE;
			}
		}
		return tempoNoturno;
	}
}
