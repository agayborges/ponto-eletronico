package com.ponto_eletronico;

import java.time.OffsetTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jornadas")
public class JornadaContorllerImpl implements JornadaContorller {

	@Autowired
	JornadaService jornadaService;
	
	@Override
	@GetMapping("/usuarios/{pis}")
	public List<Jornada> findJornada(@PathVariable String pis, JornadaRequestParam param) {
		List<Jornada> jornadas = null;
		if (param != null) {
			JornadaFindMode mode = param.getMode();
			if(mode != null) {
				switch (mode) {
				case DIA:
					jornadas = jornadaService.findJornadaPorDia(new Usuario(pis), param.getData());
					break;
				case MES:
					jornadas = jornadaService.findJornadaPorMes(new Usuario(pis), param.getMes(), param.getAno());
					break;
				case PERIODO:
					jornadas = jornadaService.findJornadaPorPeriodo(new Usuario(pis), param.getData(), param.getDataFinal());
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
	public Jornada realizarBatidaExcepcional(@PathVariable String id, OffsetTime hora) {
		Jornada jornada = new Jornada();
		try {
			jornada.setId(Long.parseLong(id));
		} catch (NumberFormatException e) {
			throw new PeException("Id com formato inválido");
		}
		return jornadaService.realizarBatidaExcepcional(jornada, hora);
	}

	
}
