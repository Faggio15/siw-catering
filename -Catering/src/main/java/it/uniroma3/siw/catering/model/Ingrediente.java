package it.uniroma3.siw.catering.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Ingrediente {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column (nullable=false)
	private String nome;
	
	//in che senso "origine"?
	@Column (nullable=false)
	private String origine;

	@Column (length=2000)
	private String descrizione;
	
	
	public Ingrediente() {
		
	}
}
