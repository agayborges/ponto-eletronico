package com.ponto_eletronico;

import java.time.OffsetTime;
import java.util.List;

public interface JornadaContorller {

	List<Jornada> findJornada(String pis, JornadaRequestParam param);
	Jornada realizarBatida(String pis);
	Jornada realizarBatidaExcepcional(String id, OffsetTime hora);
}
