package it.uniroma3.siw.catering.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.repository.IngredienteRepository;

@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Transactional
	public Ingrediente inserisci(Ingrediente ingrediente) {
		return ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public List<Ingrediente> ingredientePerNome(String nome) {
		return ingredienteRepository.findByNome(nome);
	}

	@Transactional
	public List<Ingrediente> tutti() {
		return (List<Ingrediente>) ingredienteRepository.findAll();
	}

	@Transactional
	public Ingrediente ingredientePerId(Long id) {
		Optional<Ingrediente> optional = ingredienteRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Ingrediente ingrediente) {
		List<Ingrediente> curatori = this.ingredienteRepository.findByNome(ingrediente.getNome());
		if (curatori.size() > 0)
			return true;
		else 
			return false;
	}
	
	
	@Transactional
	public void deleteIngredienteById(Long id) {
		this.ingredienteRepository.deleteById(id);
	}
}
