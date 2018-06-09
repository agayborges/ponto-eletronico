package com.ponto_eletronico;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Usuario.class)
public class Usuario_ {

	public static volatile SingularAttribute<Usuario, String> pis;
	public static volatile SingularAttribute<Usuario, String> senha;
}
