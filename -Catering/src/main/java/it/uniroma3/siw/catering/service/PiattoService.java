package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.PiattoRepository;

@Service
public class PiattoService{

	@Autowired
	private PiattoRepository piattoRepository;

	@Transactional
	public Piatto inserisci(Piatto piatto) {
		return piattoRepository.save(piatto);
	}

	@Transactional
	public List<Piatto> piattoPerNome(String piatto) {
		return piattoRepository.findByNome(piatto);
	}

	@Transactional
	public List<Piatto> tutti() {
		return (List<Piatto>) piattoRepository.findAll();
	}

	@Transactional
	public Piatto piattoPerId(Long id) {
		Optional<Piatto> optional = piattoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Piatto piatto) {
		List<Piatto> piatti = this.piattoRepository.findByNome(piatto.getNome());
		if (piatti.size() > 0)
			return true;
		else 
			return false;
	}
	

	public void deletePiattoById(Long id) {
		piattoRepository.deleteById(id);
	}

	public List<Piatto> trovaPerIngredientiPiattoId(Long id) {
		return piattoRepository.findByIngredientiPiattoId(id);
	}
}