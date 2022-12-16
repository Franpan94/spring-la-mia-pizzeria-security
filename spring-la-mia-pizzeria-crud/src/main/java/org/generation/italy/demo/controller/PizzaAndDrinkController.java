package org.generation.italy.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.generation.italy.demo.inter.PriceableInt;
import org.generation.italy.demo.pojo.Drink;
import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.serv.DrinkService;
import org.generation.italy.demo.serv.PizzeriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pizzaAndDrink")
public class PizzaAndDrinkController {

	@Autowired
	private PizzeriaService pzs;
	
	@Autowired
	private DrinkService drs;
	
	@GetMapping("/user/search")
	public String searchPizzaAndDrink(Model model, 
            @RequestParam(name = "name", required = false) String name) {
		
		List<Pizzeria> pizze = null;
		List<Drink> drinks = null;
		
		if(name == null) {
			
			pizze = pzs.findAll();
			drinks = drs.findAll();
			
		} else {
			
			pizze = pzs.findByName(name);
			drinks = drs.findByName(name);
		}
		
		model.addAttribute("name", name);
		model.addAttribute("pizze", pizze);
		model.addAttribute("drinks", drinks);
		
		return "DrinkAndPizza";
	}
	
	@GetMapping("/user/priceAbleInt")
	public String orderByPrice(Model model) {

		List<PriceableInt> priceAbleInts = new ArrayList<>();
		priceAbleInts.addAll(drs.findAll());
		priceAbleInts.addAll(pzs.findAll());
		
		priceAbleInts.sort((p1, p2) -> p1.getPrice() - p2.getPrice());
		model.addAttribute("priceAbleInts", priceAbleInts);
		
		return "PriceAbleInt";
		
	}
}
