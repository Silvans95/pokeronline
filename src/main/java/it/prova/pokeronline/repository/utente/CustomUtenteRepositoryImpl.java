package it.prova.pokeronline.repository.utente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.pokeronline.model.Utente;


public class CustomUtenteRepositoryImpl implements CustomUtenteRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Utente> findByExample(Utente example, String[] ruoli) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select r from Utente r join r.ruoli c where r.id = r.id ");

		if (StringUtils.isNotEmpty(example.getNome())) {
			whereClauses.add(" r.nome  like :nome ");
			paramaterMap.put("nome", "%" + example.getNome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getCognome())) {
			whereClauses.add(" r.cognome like :cognome ");
			paramaterMap.put("cognome", "%" + example.getCognome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getUsername())) {
			whereClauses.add(" r.username like :username ");
			paramaterMap.put("username", "%" + example.getUsername() + "%");
		}
		if (example.getStato() != null) {
			whereClauses.add(" r.stato =:stato ");
			paramaterMap.put("stato", example.getStato());
		}

		if (example.getDateCreated() != null) {
			whereClauses.add("r.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		
		if (example.getCreditoAccumulato() != null) {
			whereClauses.add("r.creditoAccumulato >= :creditoAccumulato ");
			paramaterMap.put("creditoAccumulato", example.getCreditoAccumulato());
		}
		if (example.getEsperienzaAccumulata() != null) {
			whereClauses.add("r.esperienzaAccumulata >= :esperienzaAccumulata ");
			paramaterMap.put("esperienzaAccumulata", example.getEsperienzaAccumulata());
		}

		String role = "";
		if (ruoli != null && ruoli.length > 0) {
			for (int i = 0; i < ruoli.length; i++) {
				if (i == 0)
					role += " c.id = " + ruoli[i];
				else
					role += " or c.id = " + ruoli[i];
			}
		}
		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));

		if (ruoli != null)
			queryBuilder.append(" and " + role);

		TypedQuery<Utente> typedQuery = entityManager.createQuery(queryBuilder.toString(), Utente.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
}
