package com.ponto_eletronico;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JornadaServiceImpl implements JornadaService {

	@Autowired
	JornadaRepository jornadaRepository;

	@Override
	public List<Jornada> findJornadaPorMes(Usuario usuario,int ano, int mes) {
		LocalDate localDateInicial = LocalDate.of(ano, mes, 1);
		int ultimoDiaMes = localDateInicial.getMonth().length(localDateInicial.isLeapYear());
		LocalDate localDateFinal = LocalDate.of(ano, mes, ultimoDiaMes);
		
		
		return findJornadaPorPeriodo(usuario, localDateInicial, localDateFinal);
	}

	@Override
	public List<Jornada> findJornadaPorDia(Usuario usuario, LocalDate data) {
		return findJornadaPorPeriodo(usuario, data, null);
	}

	@Override
	public List<Jornada> findJornadaPorPeriodo(Usuario usuario, LocalDate dataInicio, LocalDate dataFim) {
		return jornadaRepository.findJornadaPorPeriodo(usuario.getPis(), dataInicio, dataFim);
	}

	@Override
	public Jornada retriveJornada(Long id) {
		return jornadaRepository.findById(id).orElse(null);
	}

	@Override
	public Jornada realizarBatida(String pis) {
		OffsetDateTime dataAtual = OffsetDateTime.now();
		LocalDate dataJornada = dataAtual.toLocalDate();
		OffsetTime horaAtual = dataAtual.toOffsetTime();
		
		List<Jornada> jornadas = jornadaRepository.findJornadaPorPeriodo(pis, dataJornada, null);
		Batida batida = new Batida(horaAtual);
		
		Jornada jornada = null;
		if (jornadas.isEmpty()) {
			Usuario usuario = new Usuario();
			usuario.setPis(pis);
			jornada = new Jornada(usuario, dataJornada, batida);
		} else {
			jornada = jornadas.get(0);
			jornada.addBatida(batida);
		}
		
		jornada = jornadaRepository.save(jornada);
		
		return jornada;
	}
}
