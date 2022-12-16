package org.generation.italy.demo.serv;

import java.util.List;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.repo.IngredientRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class IngredientService {
    
	@Autowired 
	private IngredientRepo ingredientRepo;
	
	public void save(Ingredient ingredient) {
		
		ingredientRepo.save(ingredient);
	}
	
	public List<Ingredient> findAll() {
		
		return ingredientRepo.findAll();
	}
	@Transactional
	public List<Ingredient> findAllWithPizza(){
		
		List<Ingredient> ingredients = ingredientRepo.findAll();
		
		for(Ingredient ingredient : ingredients) {
			
			Hibernate.initialize(ingredient.getPizze());
		}
		
		return ingredients;
	}
	
	public void delete(Ingredient ingredient) {
		
		ingredientRepo.delete(ingredient);
	}
	
	public Ingredient findById(int id){
		
		return ingredientRepo.findById(id).get();
	}
	
	public void deleteById(int id) {
		
		ingredientRepo.deleteById(id);
	}
}
