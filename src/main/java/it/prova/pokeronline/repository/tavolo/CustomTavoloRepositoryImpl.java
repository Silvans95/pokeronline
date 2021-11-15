package it.prova.pokeronline.repository.tavolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();
		String gioc = "";

		StringBuilder queryBuilder = new StringBuilder(
				"select distinct r from Tavolo r join fetch r.utenteCreatore uc join fetch r.giocatori g where r.id = r.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add(" r.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getEsperienzaMin() >= 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", "%" + example.getEsperienzaMin() + "%");
		}
		if (example.getCifraMinima() >= 0) {
			whereClauses.add(" r.cifraMinima >= :cifraMinima ");
			paramaterMap.put("cifraMinima", example.getCifraMinima());
		}
		if (example.getUtenteCreatore() != null) {
			whereClauses.add(" uc.id = :idUtenteCreatore ");
			paramaterMap.put("idUtenteCreatore", example.getUtenteCreatore().getId());
		}

		if (example.getGiocatori() != null && example.getGiocatori().size() > 0) {
			int i = 0;
			for (Utente giocatore : example.getGiocatori()) {
				if (i == 0)
					gioc += " g.id = " + giocatore.getId();
				else
					gioc += " or g.id = " + giocatore.getId();
				i++;
			}
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		if (example.getGiocatori() != null)
			queryBuilder.append(" and " + gioc);
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

	@Override
	public List<Tavolo> findByExampleMieiTavoli(TavoloDTO example, Long id) {

		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		if (example.getEsperienzaMin() == null)
			example.setEsperienzaMin(1);

		if (example.getCifraMinima() == null)
			example.setCifraMinima(1);

		StringBuilder queryBuilder = new StringBuilder(
				"select distinct r from Tavolo r join fetch r.utenteCreatore uc where r.id = r.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add(" r.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getEsperienzaMin() > 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMinima() > 0) {
			whereClauses.add(" r.cifraMinima >= :cifraMinima ");
			paramaterMap.put("cifraMinima", example.getCifraMinima());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		queryBuilder.append(" and uc.id = " + id);
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

	@Override
	public List<Tavolo> findByExampleConCreatore(TavoloDTO example) {

		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		if (example.getEsperienzaMin() == null)
			example.setEsperienzaMin(1);

		if (example.getCifraMinima() == null)
			example.setCifraMinima(1);

		StringBuilder queryBuilder = new StringBuilder(
				"select r from Tavolo r join fetch r.utenteCreatore uc where r.id = r.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add(" r.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getEsperienzaMin() > 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMinima() > 0) {
			whereClauses.add(" r.cifraMinima >= :cifraMinima ");
			paramaterMap.put("cifraMinima", example.getCifraMinima());
		}
		if (example.getUtenteCreatore() != null && example.getUtenteCreatore().getId() != null) {
			whereClauses.add(" uc.id = :idUtenteCreatore ");
			paramaterMap.put("idUtenteCreatore", example.getUtenteCreatore().getId());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

}