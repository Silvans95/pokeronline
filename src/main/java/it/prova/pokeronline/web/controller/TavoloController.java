package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.tavolo.TavoloService;
import it.prova.pokeronline.service.utente.UtenteService;

@Controller
@RequestMapping(value = "/tavolo")
public class TavoloController {

	@Autowired
	TavoloService tavoloService;

	@Autowired
	UtenteService utenteService;

	@GetMapping
	public ModelAndView listAllTavoli() {
		ModelAndView mv = new ModelAndView();
		List<Tavolo> tavoli = tavoloService.listAllTavoli();
		mv.addObject("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		mv.setViewName("tavolo/list");
		return mv;
	}

	@GetMapping("/findMyTables")
	public String findMieiTavoli(Model model, HttpServletRequest request) {
		List<Tavolo> tavoli = tavoloService
				.listAllMyTables(utenteService.findByUsername(request.getUserPrincipal().getName()));
		model.addAttribute("tavolo_list_attribute", tavoli);
		return "tavolo/list";
	}

	@GetMapping("/search")
	public String searchTavolo() {
		return "tavolo/search";
	}

	@PostMapping("/list")
	public String listTavoli(TavoloDTO tavoloExample, Model model) {
		List<Tavolo> tavoli = tavoloService.findByExample(tavoloExample);
		
		model.addAttribute("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		return "tavolo/listUniversal";
	}

	@GetMapping("/insert")
	public String createTavolo(Model model) {
		model.addAttribute("insert_tavolo_attr", new TavoloDTO());
		return "tavolo/insert";
	}

	@PostMapping("/save")
	public String saveTavolo(@Valid @ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors())
			return "tavolo/insert";

		Utente io = utenteService.findByUsername(request.getUserPrincipal().getName());
		UtenteDTO creatore = new UtenteDTO();
		creatore.setId(io.getId());
		tavoloDTO.setUtenteCreatore(creatore);

		tavoloService.inserisciNuovo(tavoloDTO.buildTavoloModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo/findMyTables";
	}

	@GetMapping("/show/{idTavolo}")
	public String show(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloTavolo(idTavolo);
		model.addAttribute("show_tavolo_attr", tavolo);
		return "tavolo/show";
	}

	@GetMapping("/edit/{idTavolo}")
	public String edit(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloTavolo(idTavolo);
		model.addAttribute("update_tavolo_attr", tavolo);
		return "tavolo/edit";
	}

	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid @ModelAttribute("update_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request, Model model) {

		if (result.hasErrors())
			return "tavolo/edit";

		Tavolo tavolo = tavoloService.caricaSingoloTavolo(tavoloDTO.getId());

		if (tavolo.getGiocatori().size() == 0) {

			tavoloDTO.setGiocatori(tavolo.getGiocatori());
			tavoloDTO.setUtenteCreatore(UtenteDTO.buildUtenteDTOFromModel(tavolo.getUtenteCreatore()));

			tavoloService.inserisciNuovo(tavoloDTO.buildTavoloModel());
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} else
			request.setAttribute("errorMessage", "Ci sono ancora giocatori che stanno giocando");

		List<Tavolo> tavoli = tavoloService
				.listAllMyTables(utenteService.findByUsername(request.getUserPrincipal().getName()));
		model.addAttribute("tavolo_list_attribute", tavoli);

		return "tavolo/list";
	}

	@GetMapping("/delete/{idTavolo}")
	public String delete(@PathVariable(required = true) Long idTavolo, Model model) {

		Tavolo tavolo = tavoloService.caricaSingoloTavolo(idTavolo);
		model.addAttribute("delete_tavolo_attr", tavolo);
		return "tavolo/delete";
	}

	@PostMapping("/salvadelete")
	public String salvadelete(@RequestParam Long idTavolo, Model model, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {

		if (tavoloService.caricaSingoloTavoloConGiocatori(idTavolo).getGiocatori().size() == 0) {
			tavoloService.rimuoviById(idTavolo);
			request.setAttribute("successMessage", "Operazione eseguita correttamente");
		} else
			request.setAttribute("errorMessage", "Ci sono ancora giocatori che stanno giocando");

		List<Tavolo> tavoli = tavoloService
				.listAllMyTables(utenteService.findByUsername(request.getUserPrincipal().getName()));
		model.addAttribute("tavolo_list_attribute", tavoli);

		return "tavolo/list";
	}

	@GetMapping("/gestione")
	public String gestione() {
		return "tavolo/searchGestioneTavolo";
	}

	@PostMapping("/listGestione")
	public String listGestione(TavoloDTO tavoloDTO, Model model, HttpServletRequest request) {

		List<Tavolo> tavoli = tavoloService.findByExampleGestione(tavoloDTO, request.getUserPrincipal().getName());

		model.addAttribute("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		return "tavolo/list";
	}
}
