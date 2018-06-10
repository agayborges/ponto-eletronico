package com.ponto_eletronico;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jornadas")
public class JornadaContorllerImpl implements JornadaContorller {

	@Autowired
	JornadaService jornadaService;
	
	@Override
	@GetMapping
	public List<Jornada> findJornada(JornadaRequestParam param) {
		List<Jornada> jornadas = null;
		String pis = param.getPis();
		
		Usuario usuario = null;
		if (pis != null) {
			usuario = new Usuario(pis);
		}
		
		Date data = param.getData();
		Date dataFinal = param.getDataFinal();
		LocalDate dataLocal = null;
		LocalDate dataFinalLocal = null;
		
		if (data != null) {
			dataLocal = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		if (dataFinal != null) {
			dataFinalLocal = dataFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		
		if (param != null) {
			JornadaFindMode mode = param.getMode();
			if(mode != null) {
				switch (mode) {
				case DIA:
					jornadas = jornadaService.findJornadaPorDia(usuario, dataLocal);
					break;
				case MES:
					jornadas = jornadaService.findJornadaPorMes(usuario, param.getAno(), param.getMes());
					break;
				case PERIODO:
					jornadas = jornadaService.findJornadaPorPeriodo(usuario, dataLocal, dataFinalLocal);
					break;
				}
			}
		}
		return jornadas;
	}

	@Override
	@PostMapping("/usuarios/{pis}")
	public Jornada realizarBatida(@PathVariable String pis) {
		return jornadaService.realizarBatida(pis);
	}
	
	@Override
	@PostMapping("/{id}")
	public Jornada realizarBatidaExcepcional(@PathVariable String id, @RequestBody Hora hora) {
		try {
			Jornada jornada = new Jornada();
			jornada.setId(Long.parseLong(id));
			ZoneOffset offSet = OffsetDateTime.now().getOffset();
			OffsetTime offsetTime = OffsetTime.of(hora.getHora(), hora.getMinuto(), 0, 0, offSet);
			return jornadaService.realizarBatidaExcepcional(jornada, offsetTime);
		} catch (NumberFormatException e) {
			throw new PeException("Id com formato inválido");
		} catch (PeException e) {
			throw e;
		}
	}

	@Override
	@DeleteMapping("/batidas/{batidaId}")
	public Jornada removeBatida(@PathVariable long batidaId) {
		Batida batida = new Batida();
		batida.setId(batidaId);
		return jornadaService.removerBatida(batida);
	}

	@Override
	@DeleteMapping("/{id}")
	public void removeJornada(@PathVariable long id) {
		jornadaService.removeJornada(id);
	}

	@Override
	@GetMapping("/{id}")
	public Jornada obterJornada(@PathVariable long id) {
		return jornadaService.obterJornada(id);
	}

	
}
