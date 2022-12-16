package org.generation.italy.demo.pojo;

import org.generation.italy.demo.inter.PriceableInt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Drink implements PriceableInt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "Il nome deve contenere qualcosa")
	@Size(min = 3, message = "Il nome deve contenere almeno 3 caratteri")
	@Column(unique = true)
    private String name;
	
	@NotEmpty(message = "L'immagine deve contenere qualcosa")
	@Size(min=5, message="L'immagine deve contenere almeno 5 caratteri")
	@Column
	private String img;
	
	@NotEmpty(message = "La descrizione deve contenere qualcosa")
	@Size(min=5, message="La descrizione deve contenere almeno 5 caratteri")
	@Column
    private String description;
	
	@NotNull(message="Il prezzo deve avere un valore minimo di 1 e un massimo di 500")
	@Min(value = 1)
	@Max(value = 500)
	@Column
    private Integer price;
	
	public Drink() {
		
	}
	
	public Drink(String name, String img, String description, int price) {
		setDescription(description);
		setName(name);
		setImg(img);
		setPrice(price);
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return getId() + getName() + getImg() + getDescription() + getPrice();
	}
}
