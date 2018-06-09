package com.ponto_eletronico;

import java.time.LocalDate;
import java.util.List;

public interface JornadaService {

	List<Jornada> findJornadaPorMes(Usuario usuario, int ano, int mes);
	List<Jornada> findJornadaPorDia(Usuario usuario, LocalDate data);
	List<Jornada> findJornadaPorPeriodo(Usuario usuario, LocalDate dataInicio, LocalDate dataFim);
	Jornada retriveJornada(Long id);
	Jornada realizarBatida(String pis);
}
