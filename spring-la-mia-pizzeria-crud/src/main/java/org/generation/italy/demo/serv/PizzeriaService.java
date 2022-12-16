package org.generation.italy.demo.serv;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.repo.PizzeriaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzeriaService {
    
	@Autowired
	private PizzeriaRepo pizzeriaRepo;
	
	public void save(Pizzeria pizzeria) {
		
		pizzeriaRepo.save(pizzeria);
	}
	
	public List<Pizzeria> findAll(){
		
		return pizzeriaRepo.findAll();
	}
	
	public Optional<Pizzeria> findPizzaId(int id){
		
		return pizzeriaRepo.findById(id);
	}
	
	public void delete(Pizzeria pizza) {
		
		pizzeriaRepo.delete(pizza);
	}
	
	public void deleteById(int id) {
		
		pizzeriaRepo.deleteById(id);
	}
	
	public List<Pizzeria> findByName(String name) {
		
		return pizzeriaRepo.findByNameContainingIgnoreCase(name);
	}
}
