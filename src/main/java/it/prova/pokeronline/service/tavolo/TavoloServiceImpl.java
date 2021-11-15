package it.prova.pokeronline.service.tavolo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	TavoloRepository repository;
	
	@Autowired
	UtenteRepository utenteRepository;

	@Transactional(readOnly = true)
	public List<Tavolo> listAllTavoli() {
		return (List<Tavolo>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavolo(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Tavolo tavoloInstance) {
		repository.save(tavoloInstance);
	}

	@Transactional
	public void inserisciNuovo(Tavolo tavoloInstance) {
		repository.save(tavoloInstance);
	}

	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		repository.delete(tavoloInstance);
	}

	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(Tavolo example) {
		return repository.findByExample(example);
	}

	@Transactional(readOnly = true)
	public List<Tavolo> listAllMyTables(Utente user) {
		return repository.findAllByUtenteCreatore_IdIs(user.getId());
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConGiocatori(Long id) {
		return repository.findByIdConGiocatori(id).orElse(null);
	}

	@Transactional
	public void rimuoviById(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional
	public List<Tavolo> findByExampleGestione(TavoloDTO tavolo, String username) {
		
		Utente utente = utenteRepository.findByUsernameConRuoli(username).get();
		
		if(utente.isAdmin()) {
			return repository.findByExampleConCreatore(tavolo);
		}
		
		return repository.findByExampleMieiTavoli(tavolo, utente.getId());
	}

}