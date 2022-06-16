package it.uniroma3.siw.catering.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.catering.controller.validator.PiattoValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.PiattoService;
import it.uniroma3.siw.catering.util.FileUploadUtil;


@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private IngredienteService ingredienteService;
	
    @Autowired
    private PiattoValidator piattoValidator;

    @RequestMapping(value="/admin/addPiatto", method = RequestMethod.GET)
    public String addPiatto(Model model) {
    	model.addAttribute("piatto", new Piatto());
    	model.addAttribute("ingredienti", this.ingredienteService.tutti());
    	model.addAttribute("buffets", this.buffetService.tutti());
        return "admin/piattoForm";
    }
    

    @RequestMapping(value = "/piatto/{id}", method = RequestMethod.GET)
    public String getPiatto(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("piatto", this.piattoService.piattoPerId(id));
    	model.addAttribute("ingredienti", this.piattoService.piattoPerId(id).getIngredientiPiatto());
    	return "piatto.html"; 
    }

    @RequestMapping(value = "/piatti", method = RequestMethod.GET)
    public String getPiatti(Model model) {
    		model.addAttribute("piatti", this.piattoService.tutti());
    		return "piatti.html";
    }
    
    
    @RequestMapping(value = "/admin/piatto", method = RequestMethod.POST)
    public String newPiatto(@ModelAttribute("piatto") Piatto piatto,
    		 @RequestParam("image") MultipartFile multipartFile, Model model, BindingResult bindingResult) throws IOException  {
    	this.piattoValidator.validate(piatto, bindingResult);
        if (!bindingResult.hasErrors()) {
        	/*buffet.trim();
            List<Buffet> buff= this.buffetService.buffetPerNome(buffet);
            piatto.setBuffet(buff);
        	ingrediente.trim();  
            List<Ingrediente> ing = this.ingredienteService.ingredientePerNome(ingrediente); 
            piatto.setIngredientiPiatto(ing);*/
            
            /*UPLOAD FOTO*/
        	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            piatto.setPhotos(fileName);
            Piatto savedPiatto = this.piattoService.inserisci(piatto);
            String uploadDir = "src/main/resources/static/img/piatti/" + savedPiatto.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            
            model.addAttribute("piatti", this.piattoService.tutti());
            return "piatti.html";
        }
        return "admin/piattoForm.html";
    }

    
    @RequestMapping(value= "/admin/deletePiatti", method= RequestMethod.GET)
    public String deletePiattoGet(Model model) {
    	model.addAttribute("piatti", this.piattoService.tutti());
    	return "admin/deletePiatto.html";
    }
    
    @RequestMapping(value = "/admin/deletePiatto/{id}", method = RequestMethod.POST)
	public String deletePiattoPost(@PathVariable("id") Long id, Model model) throws IOException {
		
    	/*DELETE FOTO*/
    	Piatto piattoDaRimuovere = this.piattoService.piattoPerId(id);
		if(!(piattoDaRimuovere.getPhotos()==null)) {
	   	String uploadDir ="src/main/resources/static/img/piatti/"+piattoDaRimuovere.getId();
		Path uploadPath = Paths.get(uploadDir);
		FileUtils.deleteDirectory(uploadPath.toFile());
		}
		
		List<Buffet> buffs= this.buffetService.trovaPerPiattiBuffetId(id);
		for(Buffet b: buffs) {
			b.getPiattiBuffet().remove(piattoDaRimuovere);
			this.buffetService.save(b);
		}
    	this.piattoService.deletePiattoById(id);
		model.addAttribute("piatti", this.piattoService.tutti());
		return "admin/deletePiatto.html";
	}
}