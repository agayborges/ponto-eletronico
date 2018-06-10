package com.ponto_eletronico;

import java.util.Date;

public class JornadaRequestParam {

	private JornadaFindMode mode;
	private int ano;
	private int mes;
	private Date data;
	private Date dataFinal;
	private String pis;
	
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

}
