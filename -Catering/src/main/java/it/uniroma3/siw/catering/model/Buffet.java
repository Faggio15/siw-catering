package it.uniroma3.siw.catering.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Buffet {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column (nullable=false)
	private String nome;


	@Column (length=2000)
	private String descrizione;

	@ManyToOne
	private Chef chef;


	@ManyToMany(fetch = FetchType.EAGER)
	List<Piatto> piattiBuffet;
	
	public Buffet() {
		this.piattiBuffet = new ArrayList<>();
	}
	
	/*@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Collezione other = (Collezione) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}*/

}
