package com.ponto_eletronico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.OffsetTime;

import org.junit.Test;

public class JornadaTest {
	
	private OffsetTime hora01 = OffsetTime.of(8, 0, 23, 1, OffsetTime.now().getOffset());
	Batida batida01 = new Batida(hora01);
	
	private OffsetTime hora02 = OffsetTime.of(12, 0, 04, 2, OffsetTime.now().getOffset());
	Batida batida02 = new Batida(hora02);
	
	private OffsetTime hora03 = OffsetTime.of(14, 0, 53, 3, OffsetTime.now().getOffset());
	Batida batida03 = new Batida(hora03);
	
	private OffsetTime hora04 = OffsetTime.of(18, 10, 47, 4, OffsetTime.now().getOffset());
	Batida batida04 = new Batida(hora04);
	
	private OffsetTime hora05 = OffsetTime.of(23, 30, 31, 5, OffsetTime.now().getOffset());
	Batida batida05 = new Batida(hora05);
	
	private OffsetTime hora06 = OffsetTime.of(3, 20, 27, 6, OffsetTime.now().getOffset());
	Batida batida06 = new Batida(hora06);
	
	private OffsetTime hora07 = OffsetTime.of(12, 20, 19, 7, OffsetTime.now().getOffset());
	Batida batida07 = new Batida(hora07);
	
	private OffsetTime hora08 = OffsetTime.of(13, 35, 7, 8, OffsetTime.now().getOffset());
	Batida batida08 = new Batida(hora08);
	
	private OffsetTime hora09 = OffsetTime.of(12, 10, 59, 9, OffsetTime.now().getOffset());
	Batida batida09 = new Batida(hora09);
	
	// 480min em horario normal com 120min de intervalo
	// 720min no sabado / 960 min no domingo
	Batida[] jornadaNormal = {batida01, batida02, batida03, batida04};
	// 240min em horario normal
	Batida[] jornada4h = {batida01, batida02};
	// 250min em horario normal com 10min de intervalo
	Batida[] jornada6hSemIntervalo = {batida01, batida03, batida07, batida09};
	// 260min em horario normal com 20min de intervalo
	Batida[] jornada6h= {batida01, batida03, batida07, batida08};
	// 600min em horario normal sem intervalo
	// 900min no sabado / 1200 min no domingo
	Batida[] jornadaSemIntervalo = {batida01, batida04};
	// 250min em horario nortuno + 360min em horario normal -> totais 610 / 660
	// 1220min no sabado / 1320 min no domingo
	Batida[] jornadaNoturna = {batida06, batida01, batida04, batida05};
	// 555min em horario normal com 25min de intervalo
	// 832min no sabado / 1110 min no domingo
	Batida[] jornadaVariasBatidas = {batida01, batida02, batida03, batida04, batida07, batida08};
	
	@Test
	public void testNormal() {
		LocalDate diaNormal = LocalDate.of(2018, 6, 11);
		
		Jornada normal = new Jornada();
		normal.setDia(diaNormal);
		addBatidas(normal, jornadaNormal);
		assertTrue(normal.isIntervaloEstaCorreto());
		assertEquals(480, normal.getMinutosTrabalhados().intValue());
		assertEquals(120, normal.getIntervalo().intValue());
		
		normal = new Jornada();
		normal.setDia(diaNormal);
		addBatidas(normal, jornada4h);
		assertTrue(normal.isIntervaloEstaCorreto());
		assertEquals(240, normal.getMinutosTrabalhados().intValue());
		assertEquals(0, normal.getIntervalo().intValue());
		
		normal = new Jornada();
		normal.setDia(diaNormal);
		addBatidas(normal, jornada6h);
		assertTrue(normal.isIntervaloEstaCorreto());
		assertEquals(250, normal.getMinutosTrabalhados().intValue());
		assertEquals(20, normal.getIntervalo().intValue());
		
		normal = new Jornada();
		normal.setDia(diaNormal);
		addBatidas(normal, jornada6hSemIntervalo);
		assertTrue(!normal.isIntervaloEstaCorreto());
		assertEquals(250, normal.getMinutosTrabalhados().intValue());
		assertEquals(10, normal.getIntervalo().intValue());
		
		normal = new Jornada();
		normal.setDia(diaNormal);
		addBatidas(normal, jornadaSemIntervalo);
		assertTrue(!normal.isIntervaloEstaCorreto());
		assertEquals(600, normal.getMinutosTrabalhados().intValue());
		assertEquals(0, normal.getIntervalo().intValue());
		
		normal = new Jornada();
		normal.setDia(diaNormal);
		addBatidas(normal, jornadaNoturna);
		assertTrue(normal.isIntervaloEstaCorreto());
		assertEquals(660, normal.getMinutosTrabalhados().intValue());
	}
	
	@Test
	public void testSabado() {
		LocalDate diaSabado = LocalDate.of(2018, 6, 9);
		Jornada sabado = new Jornada();
		sabado.setDia(diaSabado);
		assertTrue(sabado.isIntervaloEstaCorreto());
		assertEquals(720, sabado.getMinutosTrabalhados().intValue());
		assertEquals(120, sabado.getIntervalo().intValue());
		
		sabado = new Jornada();
		sabado.setDia(diaSabado);
		addBatidas(sabado, jornadaNoturna);
		assertTrue(sabado.isIntervaloEstaCorreto());
		assertEquals(1220, sabado.getMinutosTrabalhados().intValue());
	}
	
	@Test
	public void testDomingo() {
		LocalDate diaDomingo = LocalDate.of(2018, 6, 10);
		Jornada domingo = new Jornada();
		domingo.setDia(diaDomingo);
		assertTrue(domingo.isIntervaloEstaCorreto());
		assertEquals(1200, domingo.getMinutosTrabalhados().intValue());
		assertEquals(120, domingo.getIntervalo().intValue());
		
		domingo = new Jornada();
		domingo.setDia(diaDomingo);
		addBatidas(domingo, jornadaNoturna);
		assertTrue(domingo.isIntervaloEstaCorreto());
		assertEquals(1320, domingo.getMinutosTrabalhados().intValue());
	}
	
	private void addBatidas(Jornada jornada, Batida[] batidas) {
		for (int i = 0; i < batidas.length; i++) {
			Batida batida = batidas[i];
			jornada.addBatida(batida);
		}
	}
}
