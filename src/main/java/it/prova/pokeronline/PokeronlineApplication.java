package it.prova.pokeronline;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.ruolo.RuoloService;
import it.prova.pokeronline.service.utente.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("special player", "ROLE_SPECIAL_PLAYER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("special player", "ROLE_SPECIAL_PLAYER"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("role player", "ROLE_PLAYER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("role player", "ROLE_PLAYER"));
		}

		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "nome 1", "cognome 1", new Date(), 0, 5000);
			admin.setStato(StatoUtente.ATTIVO);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			utenteServiceInstance.inserisciNuovo(admin);
		}

		if (utenteServiceInstance.findByUsername("user") == null) {
			Utente classicUser = new Utente("user", "user", "nome 2", "cognome 2", new Date(), 0, 5000);
			classicUser.setStato(StatoUtente.ATTIVO);
			classicUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("special player", "ROLE_SPECIAL_PLAYER"));
			utenteServiceInstance.inserisciNuovo(classicUser);
		}

		if (utenteServiceInstance.findByUsername("user1") == null) {
			Utente classicUser1 = new Utente("user1", "user1", "nome 3", "cognome 3", new Date(), 0, 5000);
			classicUser1.setStato(StatoUtente.ATTIVO);
			classicUser1.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("special player", "ROLE_SPECIAL_PLAYER"));
			utenteServiceInstance.inserisciNuovo(classicUser1);
		}

		if (utenteServiceInstance.findByUsername("user2") == null) {
			Utente classicUser2 = new Utente("user2", "user2", "nome 4", "cognome 4", new Date(), 0, 5000);
			classicUser2.setStato(StatoUtente.ATTIVO);
			classicUser2.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("role player", "ROLE_PLAYER"));
			utenteServiceInstance.inserisciNuovo(classicUser2);
		}

		if (utenteServiceInstance.findByUsername("user3") == null) {
			Utente classicUser2 = new Utente("user3", "user4", "nome 5", "cognome 5", new Date(), 0, 5000);
			classicUser2.setStato(StatoUtente.ATTIVO);
			classicUser2.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("role player", "ROLE_PLAYER"));
			utenteServiceInstance.inserisciNuovo(classicUser2);
		}

	}

}
