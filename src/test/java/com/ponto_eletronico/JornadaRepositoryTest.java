package com.ponto_eletronico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration
@AutoConfigureTestDatabase
public class JornadaRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private JornadaRepository jornadaRepository;
	
	private Usuario usuario = new Usuario("72551480715", "123456");
	private OffsetDateTime dateTime01 = OffsetDateTime.now();
	private OffsetTime time01 = dateTime01.toOffsetTime();	
	private Jornada jornada01 = new Jornada(usuario, dateTime01);
	
	@Before
	public void config() {
		usuario = entityManager.persistFlushFind(usuario);
		jornada01 = new Jornada(usuario, dateTime01);
		jornada01 = entityManager.persistFlushFind(jornada01);
	}
	
	@Test
	public void testSaveJornada() {
		try {
			Jornada jornada02 = new Jornada(usuario, dateTime01);
			jornadaRepository.save(jornada02);
			fail("Constraint não respeitada");
		} catch (DataIntegrityViolationException e) {
			String localizedMessage = e.getMostSpecificCause().getLocalizedMessage();
			assertTrue(localizedMessage.startsWith("Unique index or primary key violation"));
		}
		
		try {
			Jornada jornada03 = new Jornada();
			jornada03.setDia(dateTime01.toLocalDate());
			jornada03.setUsuario(usuario);
			jornadaRepository.save(jornada03);
			fail("Constraint não respeitada");
		} catch (DataIntegrityViolationException e) {
			String localizedMessage = e.getMostSpecificCause().getLocalizedMessage();
			assertTrue(localizedMessage.startsWith("Unique index or primary key violation"));
		}
		
		try {			
			Jornada jornada04 = new Jornada(usuario, dateTime01.plusMinutes(10));
			jornada04 = jornadaRepository.save(jornada04);
			fail("Constraint não respeitada");
		} catch (DataIntegrityViolationException e) {
			String localizedMessage = e.getMostSpecificCause().getLocalizedMessage();
			assertTrue(localizedMessage.startsWith("Unique index or primary key violation"));
		}
		
		Jornada jornada05 = new Jornada(usuario, dateTime01.plusDays(1));
		jornada05 = jornadaRepository.save(jornada05);
		assertTrue(!jornada05.getBatidas().isEmpty());
		Batida batidaLocal = jornada05.getBatidas().get(0);
		assertEquals(dateTime01.getHour(), batidaLocal.getHora().getHour());
		assertEquals(dateTime01.getMinute(), batidaLocal.getHora().getMinute());
	}

	@Test
	public void testBatida() {
		Batida batida = new Batida(time01);
		Batida batidaMais10Min = new Batida(time01.plusMinutes(10));
		Batida batidaMesmoMinuto = new Batida(time01.plusSeconds(10));
		
		try {
			jornada01.addBatida(batida);
			jornadaRepository.save(jornada01);
			fail("Batida salva com mesma hora");
		} catch (DataIntegrityViolationException e) {
			String localizedMessage = e.getMostSpecificCause().getLocalizedMessage();
			assertTrue(localizedMessage.startsWith("Unique index or primary key violation"));
		} finally {
			jornada01.removeBatida(batida);
		}
		
		try {
			jornada01.addBatida(batidaMesmoMinuto);
			jornadaRepository.save(jornada01);
			fail("Batida salva no mesmo minuto");
		} catch (DataIntegrityViolationException e) {
			String localizedMessage = e.getMostSpecificCause().getLocalizedMessage();
			assertTrue(localizedMessage.startsWith("Unique index or primary key violation"));
		} finally {
			jornada01.removeBatida(batidaMesmoMinuto);
		}
		
		int batidaSizeAntes = jornada01.getBatidas().size();
		jornada01.addBatida(batidaMais10Min);
		Jornada jornadaLocal = jornadaRepository.save(jornada01);
		int batidaSizeDepois = jornadaLocal.getBatidas().size();
		assertEquals(batidaSizeAntes + 1, batidaSizeDepois);
	}
	
	@Test
	public void testFind() {
		LocalDate dataJornada = dateTime01.toLocalDate();
		List<Jornada> jornadas = jornadaRepository.findJornadaPorPeriodo(usuario.getPis(), dataJornada, null);
		int size = jornadas.size();
		assertEquals(1, size);
		
		jornadas = jornadaRepository.findJornadaPorPeriodo("123", dataJornada, null);
		size = jornadas.size();
		assertEquals(0, size);
		
		jornadas = jornadaRepository.findJornadaPorPeriodo(usuario.getPis(), dataJornada.minusDays(1), null);
		size = jornadas.size();
		assertEquals(0, size);
		
		jornadas = jornadaRepository.findJornadaPorPeriodo(usuario.getPis(), dataJornada.minusDays(1), dataJornada.plusDays(2));
		size = jornadas.size();
		assertEquals(1, size);
		
		jornadas = jornadaRepository.findJornadaPorPeriodo(usuario.getPis(), dataJornada.minusDays(5), dataJornada.minusDays(3));
		size = jornadas.size();
		assertEquals(0, size);
		
		jornadas = jornadaRepository.findJornadaPorPeriodo(null, dataJornada.minusDays(1), dataJornada.plusDays(2));
		size = jornadas.size();
		assertEquals(1, size);
		
	}
}
