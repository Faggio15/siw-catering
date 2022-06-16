package it.uniroma3.siw.catering.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Chef {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column (nullable=false)
	private String nome;

	@Column (nullable=false)
	private String cognome;
	
	@Column (nullable=false)
	private String nazionalita;
	
	@Column(nullable = true, length = 64)
	private String photos;

	@Column (length=2000)
	private String descrizione;


	@OneToMany(mappedBy="chef", cascade = CascadeType.ALL)
	List<Buffet> buffetProposti;


	public Chef(){

	}
	
	 @Transient
	    public String getPhotosImagePath() {
	        if (photos == null || id == null) return null;
	         
	        return "/img/chefs/" + id + "/" + photos;
	    }
}
