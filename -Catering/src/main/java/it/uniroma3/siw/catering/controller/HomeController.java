package it.uniroma3.siw.catering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.catering.model.Piatto;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/info", method = RequestMethod.GET) 
	public String showInfoPage (Model model) {
		return "info";
	}

}
