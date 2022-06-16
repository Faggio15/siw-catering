package it.uniroma3.siw.catering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.xml.bind.v2.runtime.reflect.Accessor.SetterOnlyReflection;

import it.uniroma3.siw.catering.controller.validator.BuffetValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.service.PiattoService;
import net.bytebuddy.asm.Advice.This;
//vanno aggiunti metodi di: aggiunta/rimozione opere e curatori nella collezione corrente
//va veriricato il funzionamento di deleteCollezione (specie il path e il return)
@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private ChefService chefService;

	@Autowired
	private PiattoService piattoService;


	@Autowired
	private BuffetValidator buffetValidator;  

	@RequestMapping(value="/admin/addBuffet", method = RequestMethod.GET)
	public String addBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("chefs", this.chefService.tutti());
		model.addAttribute("piatti", this.piattoService.tutti());
		return "admin/buffetForm.html";
	}

	@RequestMapping(value = "/buffet/{id}", method = RequestMethod.GET)
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.buffetPerId(id));
		model.addAttribute("chef", this.buffetService.buffetPerId(id).getChef());
		model.addAttribute("piatti", this.buffetService.buffetPerId(id).getPiattiBuffet());
		return "buffet.html"; 
	}

	@RequestMapping(value = "/buffets", method = RequestMethod.GET)
	public String getBuffets(Model model) {
		model.addAttribute("buffets", this.buffetService.tutti());
		return "buffets.html";
	}

	@RequestMapping(value = "/admin/buffet", method = RequestMethod.POST)
	public String newBuffet(@ModelAttribute("buffet") Buffet buffet, @RequestParam("ch") String chef, 
			 Model model, BindingResult bindingResult) {
		this.buffetValidator.validate(buffet, bindingResult);
		if (!bindingResult.hasErrors()) {
			chef.trim(); //elimino spazi bianchi iniziali e finali
			String[] nomeCognome = chef.split("\\s+"); //divido le stringhe per spazi bianchi
			List<Chef> cheff = this.chefService.chefPerNomeAndCognome(nomeCognome[0], nomeCognome[1]); //prendo l'oggetto chef
			buffet.setChef(cheff.get(0));
			this.buffetService.save(buffet);
			model.addAttribute("buffets", this.buffetService.tutti());
			return "buffets.html";
		}
		return "admin/buffetForm.html";
	}

	@RequestMapping(value= "/admin/deleteBuffets", method= RequestMethod.GET)
	public String deleteBuffetGet(Model model) {
		model.addAttribute("buffets", this.buffetService.tutti());
		return "admin/deleteBuffet.html";
	}

	@RequestMapping(value = "/admin/deleteBuffet/{id}", method = RequestMethod.POST)
	public String deleteBuffetPost(@PathVariable("id") Long id, Model model) {
		this.buffetService.deleteBuffetById(id);
		model.addAttribute("buffets", this.buffetService.tutti());
		return "admin/deleteBuffet.html";
	}


	@RequestMapping(value="/admin/editBuffet/{id}", method = RequestMethod.GET)
	public String editBuffetGet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.buffetPerId(id));
		model.addAttribute("piatti", this.piattoService.tutti());
		this.buffetService.deleteBuffetById(id);
		model.addAttribute("chefs", this.chefService.tutti());
		return "admin/editBuffet.html";
	}

	@RequestMapping(value="/admin/editBuffet", method = RequestMethod.POST)
	public String editBuffetPost(@ModelAttribute("buffet") Buffet buffet, @RequestParam("ch") String chef,
			Model model, BindingResult bindingResult) {
		this.buffetValidator.validate(buffet, bindingResult);
		if (!bindingResult.hasErrors()) {
			chef.trim(); //elimino spazi bianchi iniziali e finali
			String[] nomeCognome = chef.split("\\s+"); //divido le stringhe per spazi bianchi
			List<Chef> cheff = this.chefService.chefPerNomeAndCognome(nomeCognome[0], nomeCognome[1]); //prendo l'oggetto curatore
			buffet.setChef(cheff.get(0));  //setto il curatore della collezione 
			this.buffetService.inserisci(buffet);
			model.addAttribute("buffets", this.buffetService.tutti());
			return "buffets.html";
		}
		model.addAttribute("chefs", this.chefService.tutti());
		return "admin/editBuffet.html";
	}
}