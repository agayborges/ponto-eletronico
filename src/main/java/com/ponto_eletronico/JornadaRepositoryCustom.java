package com.ponto_eletronico;

import java.time.LocalDate;
import java.util.List;

public interface JornadaRepositoryCustom {

	List<Jornada> findJornadaPorPeriodo(String usuarioPis, LocalDate dataInicio, LocalDate dataFim);
}
