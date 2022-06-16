package it.uniroma3.siw.catering.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.PiattoService;
import net.bytebuddy.asm.Advice.This;

@Controller
public class MainController {
	
	@Autowired
	private PiattoService piattoService;
	
	@RequestMapping(value = {"/", "home"}, method = RequestMethod.GET)
	public String home(Model model) {
		List<Piatto> piatti = this.piattoService.tutti();
		if(piatti.size()>=3) {
			Collections.shuffle(piatti);
			model.addAttribute("piatti", piatti.subList(0, 3));
		}
		return "home";
	}

}