package com.ponto_eletronico;

import java.util.List;

public interface JornadaContorller {

	List<Jornada> findJornada(JornadaRequestParam param);
	Jornada realizarBatida(String pis);
	Jornada realizarBatidaExcepcional(String id, Hora hora);
	Jornada removeBatida(long batidaId);
	void removeJornada(long id);
	Jornada obterJornada(long id);
}
