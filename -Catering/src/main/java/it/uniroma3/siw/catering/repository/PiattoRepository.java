package it.uniroma3.siw.catering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Piatto;

@Repository
public interface PiattoRepository extends CrudRepository<Piatto, Long> {

	public List<Piatto> findByNome(String nome);
	
	public List<Piatto> findAll();
	
	public List<Piatto> findByIngredientiPiattoId(Long id);


}
