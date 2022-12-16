package org.generation.italy.demo.pojo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;



@Entity
@Table
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "Il nome non può essere vuoto")
	@Size(min = 3, message = "Il nome deve contenere almeno 3 caratteri")
	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "ingredients")
	private List<Pizzeria> pizze;
	
	public Ingredient() {
		
	}
	
	public Ingredient(String name) {
		setName(name);
	}
	
	public Ingredient(String name, List<Pizzeria> pizze) {
		this(name);
		setPizze(pizze);
	}

	public List<Pizzeria> getPizze() {
		return pizze;
	}

	public void setPizze(List<Pizzeria> pizze) {
		this.pizze = pizze;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
