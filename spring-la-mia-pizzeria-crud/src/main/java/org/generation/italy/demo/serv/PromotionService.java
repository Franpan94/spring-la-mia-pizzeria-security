package org.generation.italy.demo.serv;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Promotion;
import org.generation.italy.demo.repo.PromotionRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PromotionService {
     
	@Autowired
	private PromotionRepo promotionRepo;
	
	public void save(Promotion promotion) {
		
		promotionRepo.save(promotion);
	}
	
	public List<Promotion> findAll() {
		
		return promotionRepo.findAll();
	}
	@Transactional
	public List<Promotion> findAllWithPizza(){
		
		List<Promotion> promotions = promotionRepo.findAll();
		
		for(Promotion promotion : promotions) {
			
			Hibernate.initialize(promotion.getPizze());
		}
		
		return promotions;
	}
	
	public void delete(Promotion promotion) {
		
		promotionRepo.delete(promotion);
	}
	
	public void deleteById(int id) {
		
		promotionRepo.deleteById(id);
	}
	
	public Optional<Promotion> findById(int id){
		
		return promotionRepo.findById(id);
	}
}
