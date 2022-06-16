package it.uniroma3.siw.catering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.catering.model.Ingrediente;

@Repository
public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

	public List<Ingrediente> findByNome(String nome);
}