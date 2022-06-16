package it.uniroma3.siw.catering.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Piatto {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column (nullable=false)
	private String nome;


	@Column (length=2000)
	private String descrizione;


	@Column(nullable = true, length = 64)
	private String photos;

	
	@ManyToMany
	List<Ingrediente> ingredientiPiatto;


	public Piatto() {
		this.ingredientiPiatto = new ArrayList<>();
	}


		@Transient
		public String getPhotosImagePath() {
			if (photos == null || id == null) return null;

			return "/img/piatti/" + id + "/" + photos;
		}
}