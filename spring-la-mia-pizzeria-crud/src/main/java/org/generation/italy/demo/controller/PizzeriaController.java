package org.generation.italy.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.pojo.Promotion;
import org.generation.italy.demo.serv.IngredientService;
import org.generation.italy.demo.serv.PizzeriaService;
import org.generation.italy.demo.serv.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class PizzeriaController {
    
    @Autowired
    private PizzeriaService pizzeriaService;
    
    @Autowired
    private PromotionService promotionService;
    
    @Autowired
    private IngredientService ingredientService;
    
    @GetMapping
    public String getHome() {
    	
    	return "home";
    }
    
	@GetMapping("/pizza/user")
	public String index(Model model) {
		
		List<Pizzeria> pizze = pizzeriaService.findAll();
		model.addAttribute("pizze", pizze);
		
		return "Pizze";
	}
	
	@GetMapping("/pizza/user/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		
		Optional<Pizzeria> optPizza = pizzeriaService.findPizzaId(id);
		Pizzeria pizza = optPizza.get();
		model.addAttribute("pizza", pizza);
		
		return "Pizza";
	}
	
	@GetMapping("/pizza/admin/create")
	public String create(Model model) {
		
		Pizzeria pizza = new Pizzeria();
		model.addAttribute("pizza", pizza);
		
		List<Promotion> promotions = promotionService.findAllWithPizza();
		model.addAttribute("promotions", promotions);
		
		List<Ingredient> ingredients = ingredientService.findAllWithPizza();
		model.addAttribute("ingredients", ingredients);
		
		return "PizzaCreate";
	}
	
	@PostMapping("/pizza/admin/store")
	public String store(@Valid @ModelAttribute("pizza") Pizzeria pizza,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
        if (bindingResult.hasErrors()) {
			
			System.err.println("ERROR ------------------------------------------");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------------------------------------------------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/pizza/admin/create";
		}
		
        try {
			
			pizzeriaService.save(pizza);
			
		}catch(Exception e) {
			
			String message = "Il nome deve essere unico";
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/pizza/admin/create";
		}
		
		return "redirect:/pizza/user";
	}
	
	@GetMapping("/pizza/admin/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		
		Optional<Pizzeria> optPizza = pizzeriaService.findPizzaId(id);
		Pizzeria pizza = optPizza.get();
		model.addAttribute("pizza", pizza);
		
		List<Promotion> promotions = promotionService.findAllWithPizza();
		model.addAttribute("promotions", promotions);
		
		List<Ingredient> ingredients = ingredientService.findAllWithPizza();
		model.addAttribute("ingredients", ingredients);
		
		return "PizzaEdit";
	}
	
	@PostMapping("/pizza/admin/update")
	public String update(@Valid Pizzeria pizza,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
       if (bindingResult.hasErrors()) {
			
			System.err.println("ERROR ------------------------------------------");
			System.err.println(bindingResult.getAllErrors());
			System.err.println("------------------------------------------------");
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/pizza/admin/edit/" + pizza.getId();
		}
		
        try {
			
			pizzeriaService.save(pizza);
			
		}catch(Exception e) {
			
			String message = "Il nome deve essere unico";
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/pizza/admin/edit/" + pizza.getId();
		}
		
		return "redirect:/pizza/user";
	}
	
	@GetMapping("/pizza/admin/delete/{id}")
	public String delete(@PathVariable("id") int id, 
			             RedirectAttributes redirectAttributes) {
		
		try {
			
			Optional<Pizzeria> optPizza = pizzeriaService.findPizzaId(id);
			Pizzeria pizza = optPizza.get();
			pizzeriaService.delete(pizza);
			
		} catch(Exception e) {
			
			String message = "Operazione non permessa";
			redirectAttributes.addFlashAttribute("message", message);
		}
		
		return "redirect:/pizza/user";
	}
	
	@GetMapping("/pizza/user/search")
	public String searchByName(Model model, 
			                   @RequestParam(name = "name", required = false) String name) {
		
		List<Pizzeria> pizze = null;
		
		if(name == null) {
			
			pizze = pizzeriaService.findAll();
			
		} else {
			
			pizze = pizzeriaService.findByName(name);
		}
		
		model.addAttribute("name", name);
		model.addAttribute("pizze", pizze);
		
		return "PizzaSearch";
	}
}
