package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.ruolo.RuoloService;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping("/resetUserPassword")
	public String resetUserPassword(Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("password_utente_attr",
				UtenteDTO.buildUtenteDTOFromModel(utenteService.findByUsername(userDetails.getUsername())));
		return "user/resetpassword";
	}

	@PostMapping("/saveResetUserPassword")
	public String saveResetUserPassword(RedirectAttributes redirectAttrs, HttpServletRequest request) {

		String vecchiaPassword = request.getParameter("oldpassword");
		String nuovaPassword = request.getParameter("password");
		String confermaPassword = request.getParameter("confermaPassword");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String usernameUtenteSessione = userDetails.getUsername();

		Utente utenteInSessione = utenteService.findByUsername(usernameUtenteSessione);

		utenteService.cambiaPassword(nuovaPassword, vecchiaPassword, confermaPassword, utenteInSessione);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/logout";
	}

	@GetMapping("/autoInsert")
	public String autoInsert(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "/user/insert";
	}

	// per la validazione devo usare i groups in quanto nella insert devo validare
	// la pwd, nella edit no
	@PostMapping("/save")
	public String save(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "/user/insert";
		}
		
		Long[] id = {3L};
		utenteDTO.setRuoliIds(id);
		
		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:login";
	}
	

	@GetMapping(value = "/searchUtentiAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String searchTavolo(@RequestParam String term) {

		List<Utente> listaTavoloByTerm = utenteService.cercaByCognomeENomeILike(term);
		return buildJsonResponse(listaTavoloByTerm);
	}

	private String buildJsonResponse(List<Utente> listaUtenti) {
		JsonArray ja = new JsonArray();

		for (Utente utenteItem : listaUtenti) {
			JsonObject jo = new JsonObject();
			jo.addProperty("value", utenteItem.getId());
			jo.addProperty("label", utenteItem.getNome() + " " + utenteItem.getCognome());
			ja.add(jo);
		}

		return new Gson().toJson(ja);
	}

	@GetMapping("/caricaParametri")
	public @ResponseBody String caricaParametri(HttpServletRequest request) {
		
		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		return buildJsonResponseSingleUser(utente);
	}
	private String buildJsonResponseSingleUser(Utente utente) {
		JsonArray ja = new JsonArray();

		JsonObject jo = new JsonObject();
		jo.addProperty("credito", utente.getCreditoAccumulato());
		jo.addProperty("exp", utente.getEsperienzaAccumulata());
		ja.add(jo);

		return new Gson().toJson(ja);
	}
}
