package org.generation.italy.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.pojo.Promotion;
import org.generation.italy.demo.serv.PizzeriaService;
import org.generation.italy.demo.serv.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/promotion")
public class PromotionController {
     
	@Autowired 
	private PizzeriaService prs;
	
	@Autowired
	private PromotionService prts;
	
	@GetMapping
	public String index(Model model) {
		
		List<Promotion> promotions = prts.findAllWithPizza();
		model.addAttribute("promotions", promotions);
		
		return "Promotions";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		
		Promotion promotion = new Promotion();
		model.addAttribute("promotion", promotion);
		
		List<Pizzeria> pizze = prs.findAll();
		model.addAttribute("pizze", pizze);
		
		return "PromotionCreate";
	}
	
	@PostMapping("/store")
	public String store(@Valid Promotion promotion,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
        if(bindingResult.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/promotion/create";
		}
		
		
		try {
			
			List<Pizzeria> pizzaPromotions = promotion.getPizze();
			for(Pizzeria pzz : pizzaPromotions) {
				
				pzz.setPromotion(promotion);
			}
	        prts.save(promotion);
	        
		}catch (Exception e) {

			String message = "Il nome deve essere unico";
			redirectAttributes.addFlashAttribute("message", message);
			
			return "redirect:/promotion/create";
		}
        
        return "redirect:/promotion";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		
		List<Pizzeria> pizze = prs.findAll();
		model.addAttribute("pizze", pizze);
		
		Optional<Promotion> promotion = prts.findById(id);
		model.addAttribute("promotion", promotion);
		
		return "PromotionEdit";
	}
	
	@PostMapping("/update")
	public String update(@Valid Promotion promotion,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/promotion/edit/" + promotion.getId();
		}
		
		try {
	        
			List<Pizzeria> pizze = promotion.getPizze();
	        
	        for(Pizzeria p : pizze) {
	        	p.setPromotion(promotion);
	        }
			
	        prts.save(promotion);
		
		}catch (Exception e) {

			String message = "Il nome deve essere unico";
			redirectAttributes.addFlashAttribute("message", message);
			
			return "redirect:/promotion/edit/" + promotion.getId();
			
			
		}
		
		return "redirect:/promotion";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		
        try {
			
			Optional<Promotion> optPromotion = prts.findById(id);
			Promotion promotion = optPromotion.get();
			prts.delete(promotion);
			
		} catch(Exception e) {
			
			String message = "Operazione non permessa";
			redirectAttributes.addFlashAttribute("message", message);
		}
		
		return "redirect:/promotion";
	}
}
