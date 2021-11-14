package it.prova.pokeronline.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.service.ruolo.RuoloService;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;

@Controller
public class LoginController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;


	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			Model model, HttpServletRequest request) {
		if (error != null) {
			model.addAttribute("errorMessage", 
                     getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		return "login";
	}
	
	//questo mi serve per fare il display di un messaggio diverso in caso di account bloccato
    private String getErrorMessage(HttpServletRequest request, String key){
    
        Exception exception = 
                   (Exception) request.getSession().getAttribute(key);
        
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        }else if(exception instanceof LockedException) {
            error = "Attenzione! Account disabilitato";
        }else if(exception instanceof DisabledException) {
            error = "Attenzione! Account non abilitato";
        }else{
            error = "Invalid username and password!";
        }
        
        return error;
    }

	@RequestMapping(value = "/executeLogout", method = RequestMethod.GET)
	public String logoutPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//dovrebbe essere già impostato a null dalle impostazioni invalidateHttpSession(true)
		//nel SecSecurityConfig ma il controllo si fa comunque
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute("infoMessage", "You have been successfully logged out !!");
		return "login";
	}
	
	@RequestMapping(value = "/accessDenied", method = {RequestMethod.POST,RequestMethod.GET})
	public String accessDenied(Model model) {
		model.addAttribute("errorMessage", "Attenzione! Non si dispone delle autorizzazioni per accedere alla funzionalità richiesta.");
		return "index";
	}
	
	@RequestMapping(value = "/autoInsert", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "/nsert";
	}

	// per la validazione devo usare i groups in quanto nella insert devo validare
	// la pwd, nella edit no
	@RequestMapping(value = "/save", method = {RequestMethod.POST,RequestMethod.GET})
	public String save(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");
		
		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "/insert";
		}
		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:login";
	}

}
