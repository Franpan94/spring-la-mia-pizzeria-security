package org.generation.italy.demo.controller;

import java.util.List;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.serv.IngredientService;
import org.generation.italy.demo.serv.PizzeriaService;
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
@RequestMapping("/ingredient")
public class IngredientController {
   
	@Autowired
	private IngredientService ingredientServ;
	
	@Autowired
	private PizzeriaService pizzeriaServ;
	
	@GetMapping
	public String index(Model model) {
		
		List<Ingredient> ingredients = ingredientServ.findAllWithPizza();
		model.addAttribute("ingredients", ingredients);
		
		return "Ingredients";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		
		Ingredient ingredient = new Ingredient();
		model.addAttribute("ingredient", ingredient);
		
		List<Pizzeria> pizze = pizzeriaServ.findAll();
		model.addAttribute("pizze", pizze);
		
		return "IngredientCreate";
	}
	
	@PostMapping("/store")
	public String store(@Valid Ingredient ingredient,
				BindingResult bindingResult, RedirectAttributes redirectAttributes) {
			
			if(bindingResult.hasErrors()) {
				
				redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
				return "redirect:/ingredient/create";
			}
			
			try {
				
				List<Pizzeria> ingregredientPizze = ingredient.getPizze();
				for(Pizzeria pizza : ingregredientPizze) {
					
					pizza.getIngredients().add(ingredient);
				}
				
				ingredientServ.save(ingredient);
				
			}catch (Exception e) {
				
				String message = "Il nome deve essere unico";
				redirectAttributes.addFlashAttribute("message", message);
				return "redirect:/ingredient/create";
			}
			return "redirect:/ingredient";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		
		List<Pizzeria> pizze = pizzeriaServ.findAll();
		model.addAttribute("pizze", pizze);
		
		
		Ingredient ingredient = ingredientServ.findById(id);
		model.addAttribute("ingredient", ingredient);
		
		return "IngredientEdit";
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") int id, @Valid Ingredient ingredient,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/ingredient/edit/" + id;
		}
		
		try {
			
			Ingredient oldIngredient = ingredientServ.findById(id);
			for(Pizzeria pizza : oldIngredient.getPizze()) {
				pizza.getIngredients().remove(oldIngredient);
			}
			
		    List<Pizzeria> pizzeIngredient = ingredient.getPizze();
			for(Pizzeria pzz : pizzeIngredient) {
				pzz.addIngredients(ingredient);
			}
			ingredientServ.save(ingredient);
		
		}catch (Exception e) {

			String message = "Il nome deve essere unico";
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/ingredient/edit/" + id;
		}
		
		return "redirect:/ingredient";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		
        try {
			Ingredient ingredient = ingredientServ.findById(id);
			
			for(Pizzeria p : ingredient.getPizze()) {
				p.getIngredients().remove(ingredient);
			}
			ingredientServ.deleteById(id);
			
		} catch(Exception e) {
			
			String message = "Operazione non permessa";
			redirectAttributes.addFlashAttribute("message", message);
		}
		
		return "redirect:/ingredient";
	}
}
