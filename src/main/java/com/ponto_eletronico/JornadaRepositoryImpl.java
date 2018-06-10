package com.ponto_eletronico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

@Service
public class JornadaRepositoryImpl implements JornadaRepositoryCustom {

	@PersistenceContext
    private EntityManager em;

	@Override
	public List<Jornada> findJornadaPorPeriodo(String usuarioPis, LocalDate dataInicio, LocalDate dataFim) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Jornada> query = cb.createQuery(Jornada.class);
        Root<Jornada> r = query.from(Jornada.class);
        
        List<Predicate> predicateList = new ArrayList<Predicate>();
        
        if (usuarioPis != null) {
        	Predicate usuarioPredicate = cb.equal(r.get(Jornada_.usuario).get(Usuario_.pis), usuarioPis);
        	predicateList.add(usuarioPredicate);
        }
        
        if (dataFim != null) {
        	Predicate greaterThanPredicate = cb.greaterThan(r.get(Jornada_.dia), dataInicio);
        	Predicate lessThanPredicate = cb.lessThan(r.get(Jornada_.dia), dataFim);
        	predicateList.add(greaterThanPredicate);
        	predicateList.add(lessThanPredicate);
        } else {
        	Predicate dataPredicate = cb.equal(r.get(Jornada_.dia), dataInicio);
        	predicateList.add(dataPredicate);
        }
        query.where(cb.and(predicateList.toArray(new Predicate[predicateList.size()])));
		return em.createQuery(query).getResultList();
	}

	@Override
	public Jornada findJornadaPorBatida(Batida batida) throws NonUniqueResultException {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Jornada> query = cb.createQuery(Jornada.class);
        Root<Jornada> r = query.from(Jornada.class);
        
        Join<Jornada, Batida> batidaRoot = r.join(Jornada_.batidas);
        
        query.where(cb.equal(batidaRoot.get(Batida_.id), batida.getId()));
        
		return em.createQuery(query).getSingleResult();
	}

}
