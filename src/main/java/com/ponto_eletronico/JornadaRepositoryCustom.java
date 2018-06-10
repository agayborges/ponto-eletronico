package com.ponto_eletronico;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NonUniqueResultException;

public interface JornadaRepositoryCustom {

	List<Jornada> findJornadaPorPeriodo(String usuarioPis, LocalDate dataInicio, LocalDate dataFim);
	Jornada findJornadaPorBatida(Batida batida) throws NonUniqueResultException;
}
