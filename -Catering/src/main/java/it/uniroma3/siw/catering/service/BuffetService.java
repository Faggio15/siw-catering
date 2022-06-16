package it.uniroma3.siw.catering.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional
	public Buffet inserisci(Buffet buffet) {
		return buffetRepository.save(buffet);
	}
	
	@Transactional
	public void save (Buffet buffet) {
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public List<Buffet> buffetPerNome(String nome) {
		return buffetRepository.findByNome(nome);
	}

	@Transactional
	public List<Buffet> tutti() {
		return (List<Buffet>) buffetRepository.findAll();
	}

	@Transactional
	public Buffet buffetPerId(Long id) {
		Optional<Buffet> optional = buffetRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Buffet buffet) {
		List<Buffet> collezioni = this.buffetRepository.findByNome(buffet.getNome());
		if (collezioni.size() > 0)
			return true;
		else 
			return false;
	}

	public List<Buffet> trovaPerChefId(Long id){
		return this.buffetRepository.findByChefId(id);
	}


	public List<Buffet> trovaPerPiattiBuffetId(Long id){
		return this.buffetRepository.findByPiattiBuffetId(id);
	}
		
	@Transactional
	public void deleteBuffetById(Long id) {
		buffetRepository.deleteById(id);
	}


}
