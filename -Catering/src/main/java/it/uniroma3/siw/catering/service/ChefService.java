package it.uniroma3.siw.catering.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional
	public Chef inserisci(Chef chef) {
		return chefRepository.save(chef);
	}
	
	@Transactional
	public List<Chef> chefPerNomeAndCognome(String nome, String cognome) {
		return chefRepository.findByNomeAndCognome(nome, cognome);
	}

	@Transactional
	public List<Chef> tutti() {
		return (List<Chef>) chefRepository.findAll();
	}

	@Transactional
	public Chef chefPerId(Long id) {
		Optional<Chef> optional = chefRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Chef chef) {
		List<Chef> artisti = this.chefRepository.findByNomeAndCognome(chef.getNome(), chef.getCognome());
		if (artisti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteChefById(Long id) {
		this.chefRepository.deleteById(id);
	}
}
