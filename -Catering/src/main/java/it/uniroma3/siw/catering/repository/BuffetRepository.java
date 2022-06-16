package it.uniroma3.siw.catering.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.catering.model.Buffet;

@Repository
public interface BuffetRepository extends CrudRepository<Buffet, Long> {

	public List<Buffet> findByNome(String nome);

	public List<Buffet> findByChefId(Long id);
	
	public List<Buffet> findByPiattiBuffetId(Long id);
}