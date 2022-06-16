package it.uniroma3.siw.catering.controller;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.catering.controller.validator.IngredienteValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired PiattoService piattoService;
	
    @Autowired
    private IngredienteValidator ingredienteValidator;
    

    @RequestMapping(value="/addIngrediente", method = RequestMethod.GET)
    public String addIngrediente(Model model) {
    	model.addAttribute("ingrediente", new Ingrediente());
        return "ingredienteForm.html";
    }

    @RequestMapping(value = "/ingrediente/{id}", method = RequestMethod.GET)
    public String getIngrediente(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("ingrediente", this.ingredienteService.ingredientePerId(id));
    	return "ingrediente.html"; 
    }

    @RequestMapping(value = "/ingredienti", method = RequestMethod.GET)
    public String getIngredienti(Model model) {
    		model.addAttribute("ingredienti", this.ingredienteService.tutti());
    		return "ingredienti.html";//ancora non esiste
    }
    
    @RequestMapping(value = "/ingrediente", method = RequestMethod.POST)
    public String newIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente, 
    									Model model, BindingResult bindingResult) {
    	this.ingredienteValidator.validate(ingrediente, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.ingredienteService.inserisci(ingrediente);
            model.addAttribute("ingrediente", this.ingredienteService.tutti());
            return "ingredienti.html";//ancora non esiste
        }
        return "ingredienteForm.html";//ancora non esiste
    }
    
    @RequestMapping(value= "/admin/deleteIngredienti", method= RequestMethod.GET)
    public String deleteIngredienteGet(Model model) {
    	model.addAttribute("ingredienti", this.ingredienteService.tutti());
    	return "admin/deleteIngrediente.html";
    }
    
    @RequestMapping(value = "/admin/deleteIngrediente/{id}", method = RequestMethod.POST)
	public String deleteIngredientePost(@PathVariable("id") Long id, Model model) throws IOException {
		Ingrediente ingredienteDaRimuovere = this.ingredienteService.ingredientePerId(id);
		List<Piatto> piatts= this.piattoService.trovaPerIngredientiPiattoId(id);
		for(Piatto p: piatts) {
			p.getIngredientiPiatto().remove(ingredienteDaRimuovere);
			this.piattoService.inserisci(p);
		}
    	this.ingredienteService.deleteIngredienteById(id);
		model.addAttribute("ingredienti", this.ingredienteService.tutti());
		return "admin/deleteIngrediente.html";
	}
    
    
}