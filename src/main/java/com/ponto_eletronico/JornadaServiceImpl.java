package com.ponto_eletronico;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JornadaServiceImpl implements JornadaService {

	@Autowired
	JornadaRepository jornadaRepository;

	@Override
	public List<Jornada> findJornadaPorMes(Usuario usuario, int ano, int mes) {
		this.findJornadaPorMesValidParam(ano, mes);
		
		LocalDate localDateInicial = LocalDate.of(ano, mes, 1);
		int ultimoDiaMes = localDateInicial.getMonth().length(localDateInicial.isLeapYear());
		LocalDate localDateFinal = LocalDate.of(ano, mes, ultimoDiaMes);
		
		return findJornadaPorPeriodo(usuario, localDateInicial, localDateFinal);
	}
	
	private void findJornadaPorMesValidParam(int ano, int mes) {
		String erro = "";
		if (ano < 2000 || ano > 294276) {
			erro = "Ano invalido";
		}
		
		if (mes < 1 || mes > 12) {
			if (erro != null) {
				erro += "/n";
			}
			erro += "Mês invalido";
		}
		
		if (erro.length() > 0) {
			throw new PeException(erro);
		}
	}

	@Override
	public List<Jornada> findJornadaPorDia(Usuario usuario, LocalDate data) {
		return findJornadaPorPeriodo(usuario, data, null);
	}

	@Override
	public List<Jornada> findJornadaPorPeriodo(Usuario usuario, LocalDate dataInicio, LocalDate dataFim) {
		if (dataInicio == null) {
			throw new PeException("Data Inicio não pode ser nula");
		}
		return jornadaRepository.findJornadaPorPeriodo(usuario.getPis(), dataInicio, dataFim);
	}

	@Override
	public Jornada retriveJornada(Long id) {
		return jornadaRepository.findById(id).orElse(null);
	}

	@Override
	public Jornada realizarBatida(String pis) {
		if (pis == null) {
			throw new PeException("O pis não pode ser nulo");
		}
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
	
	@Override
	public Jornada realizarBatidaExcepcional(Jornada jornada, OffsetTime hora) {
		Optional<Jornada> jornadaRecuperada = jornadaRepository.findById(jornada.getId());
		
		if (jornadaRecuperada.isPresent()) {
			jornada = jornadaRecuperada.get();
			Batida batida = new Batida(hora);
			jornada.addBatida(batida);
			jornada = jornadaRepository.save(jornada);
		} else {
			throw new PeException("Jornada inexistente");
		}
		
		return jornada;
	}
}
