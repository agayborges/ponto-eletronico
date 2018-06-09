package com.ponto_eletronico;

import java.time.LocalDate;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Jornada.class)
public class Jornada_ {

	public static volatile SingularAttribute<Jornada, Long> id;
	public static volatile SingularAttribute<Jornada, LocalDate> dia;
	public static volatile SingularAttribute<Jornada, Double> horasTrabalhadas;
	public static volatile SingularAttribute<Jornada, Double> intervalo;
	public static volatile SingularAttribute<Jornada, Boolean> intervaloEstaCorreto;
	public static volatile SingularAttribute<Jornada, Usuario> usuario;

	public static volatile ListAttribute<Jornada, Batida> batidas;
}
