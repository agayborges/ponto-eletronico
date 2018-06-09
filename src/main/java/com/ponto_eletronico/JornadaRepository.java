package com.ponto_eletronico;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaRepository extends CrudRepository<Jornada, Long>, JornadaRepositoryCustom {

}
