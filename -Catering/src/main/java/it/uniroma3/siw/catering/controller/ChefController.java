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

import it.uniroma3.siw.catering.controller.validator.ChefValidator;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.util.FileUploadUtil;


@Controller
public class ChefController {
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetService buffetService;
	
    @Autowired
    private ChefValidator chefValidator;
    
    @RequestMapping(value="/admin/addChef", method = RequestMethod.GET)
    public String addChef(Model model) {
    	model.addAttribute("chef", new Chef());
        return "admin/chefForm.html";
    }
    
    @RequestMapping(value = "/chef/{id}", method = RequestMethod.GET)
    public String getChefPerId(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("chef", this.chefService.chefPerId(id));
    	model.addAttribute("buffets", this.buffetService.trovaPerChefId(id));
    	return "chef.html"; 
    }


    @RequestMapping(value = "/chefs", method = RequestMethod.GET)
    public String getChefs(Model model) {
    		model.addAttribute("chefs", this.chefService.tutti());
    		return "chefs.html";
    }
    
    @RequestMapping(value = "/admin/chef", method = RequestMethod.POST)
    public String newChef(@ModelAttribute("chef") Chef chef, @RequestParam("image") MultipartFile multipartFile,
    									Model model, BindingResult bindingResult) throws IOException {
    	this.chefValidator.validate(chef, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	
        	/*UPLOAD FOTO*/
        	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            chef.setPhotos(fileName);
            Chef savedChef = this.chefService.inserisci(chef);
            String uploadDir = "src/main/resources/static/img/chefs/" + savedChef.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            
            model.addAttribute("chefs", this.chefService.tutti());
            return "chefs.html";
        }
        return "admin/chefForm.html";
    }
    
    @RequestMapping(value= "/admin/deleteChefs", method= RequestMethod.GET)
    public String deleteChefGet(Model model) {
    	model.addAttribute("chefs", this.chefService.tutti());
    	return "admin/deleteChef.html";
    }
    
    @RequestMapping(value = "/admin/deleteChef/{id}", method = RequestMethod.POST)
	public String deleteChefPost(@PathVariable("id") Long id, Model model) throws IOException {
    	
    	/*DELETE FOTO*/
    	Chef chefDaRimuovere = this.chefService.chefPerId(id);
		if(!(chefDaRimuovere.getPhotos()==null)) {
	   	String uploadDir ="src/main/resources/static/img/chefs/"+ chefDaRimuovere.getId();
		Path uploadPath = Paths.get(uploadDir);
		FileUtils.deleteDirectory(uploadPath.toFile());
		}
		
    	this.chefService.deleteChefById(id);
		model.addAttribute("chefs", this.chefService.tutti());
		return "admin/deleteChef.html";
	}
    
    
    
    
    
    @RequestMapping(value = "/admin/editChef/{id}", method = RequestMethod.GET)
    public String editChefGet(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("chef", this.chefService.chefPerId(id));
    	this.chefService.deleteChefById(id);
    	return "admin/editChef.html"; 
    }
    
    @RequestMapping(value = "/admin/editChef", method = RequestMethod.POST)
    public String editChefPost(@ModelAttribute("chef") Chef chef,  @RequestParam("image") MultipartFile multipartFile, Model model, BindingResult bindingResult) throws IOException  {
    	
        
    	this.chefValidator.validate(chef, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
            
            /*DELETE CARTELLA FOTO*/
    		if(!(chef.getPhotos()==null)) {
    	   	String uploadDir ="src/main/resources/static/img/chefs/"+ chef.getId();
    		Path uploadPath = Paths.get(uploadDir);
    		FileUtils.deleteDirectory(uploadPath.toFile());;
    		}
            
    		/*UPLOAD FOTO*/
        	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            chef.setPhotos(fileName);
            Chef savedChef = this.chefService.inserisci(chef);
            String uploadDir = "src/main/resources/static/img/chefs/" + savedChef.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            
            model.addAttribute("chefs", this.chefService.tutti());
            return "chefs.html";
        }
        
    	
        return "admin/editChef.html";
    }
}