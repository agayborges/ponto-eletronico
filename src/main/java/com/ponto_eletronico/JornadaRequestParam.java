package com.ponto_eletronico;

import java.time.LocalDate;

public class JornadaRequestParam {

	private JornadaFindMode mode;
	private int ano;
	private int mes;
	private LocalDate data;
	private LocalDate dataFinal;
	
	public JornadaRequestParam() {
		// TODO Auto-generated constructor stub
	}

	public JornadaFindMode getMode() {
		return mode;
	}

	public void setMode(JornadaFindMode mode) {
		this.mode = mode;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

}
