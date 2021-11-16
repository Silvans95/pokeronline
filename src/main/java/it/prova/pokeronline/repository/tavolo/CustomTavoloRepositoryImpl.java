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

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tavolo> findByExample(TavoloDTO example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		if (example.getEsperienzaMin() == null)
			example.setEsperienzaMin(1);

		if (example.getCifraMinima() == null)
			example.setCifraMinima(1);

		StringBuilder queryBuilder = new StringBuilder(
				"select distinct r from Tavolo r join fetch r.utenteCreatore uc left join fetch r.giocatori gio where r.id = r.id ");

		if (StringUtils.isNotBlank(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add(" r.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getEsperienzaMin() >= 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMinima() >= 0) {
			whereClauses.add(" r.cifraMinima >= :cifraMinima ");
			paramaterMap.put("cifraMinima", example.getCifraMinima());
		}
		if (example.getUtenteCreatore() != null && example.getUtenteCreatore().getId() != null) {
			whereClauses.add(" uc.id = :idUtenteCreatore ");
			paramaterMap.put("idUtenteCreatore", example.getUtenteCreatore().getId());
		}
		if (example.getGiocatoreCercato() != null) {
			whereClauses.add(" gio.id = :giocatoreCercatoId ");
			paramaterMap.put("giocatoreCercatoId", example.getGiocatoreCercato().getId());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		System.out.println(queryBuilder);
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
			example.setEsperienzaMin(0);

		if (example.getCifraMinima() == null)
			example.setCifraMinima(0);

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