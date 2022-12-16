package org.generation.italy.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.generation.italy.demo.pojo.Drink;
import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.pojo.Promotion;
import org.generation.italy.demo.pojo.Role;
import org.generation.italy.demo.pojo.User;
import org.generation.italy.demo.serv.DrinkService;
import org.generation.italy.demo.serv.IngredientService;
import org.generation.italy.demo.serv.PizzeriaService;
import org.generation.italy.demo.serv.PromotionService;
import org.generation.italy.demo.serv.RoleService;
import org.generation.italy.demo.serv.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner {
	
	@Autowired
	private PizzeriaService pizzeriaService;
	
	@Autowired
	private DrinkService drinkService;
	
	@Autowired PromotionService promotionService;
	
	@Autowired 
	private IngredientService ingredientService ;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Creazione promozioni
	    Promotion pr1 = new Promotion(LocalDate.of(2022, 12, 23),LocalDate.of(2023, 01, 15), "Promozione di fine anno");
		Promotion pr2 = new Promotion(LocalDate.of(2023, 04, 14),LocalDate.of(2023, 05, 29), "Promozione di primavera");
		Promotion pr3 = new Promotion(LocalDate.of(2023, 06, 01),LocalDate.of(2023, 10, 25), "Promozioni estive");
				
		promotionService.save(pr1);
		promotionService.save(pr2);
		promotionService.save(pr3);
		
		Ingredient i1 = new Ingredient("Salame piccante");
		Ingredient i2 = new Ingredient("Salame dolce");
		Ingredient i3 = new Ingredient("Prosciutto cotto");
		Ingredient i4 = new Ingredient("Prosciutto crudo");
		Ingredient i5 = new Ingredient("Mozzarella");
		Ingredient i6 = new Ingredient("Rucola");
		Ingredient i7 = new Ingredient("Funghi");
		Ingredient i8 = new Ingredient("Insalata");
		Ingredient i9 = new Ingredient("Kebab");
		Ingredient i10 = new Ingredient("Scamorza affumicata");
		
		List<Ingredient> ingr1 = new ArrayList<>();
		ingr1.add(i1);
		ingr1.add(i2);
		ingr1.add(i3);
		ingr1.add(i4);
		
		List<Ingredient> ingr2 = new ArrayList<>();
		ingr2.add(i10);
		ingr2.add(i2);
		ingr2.add(i5);
		ingr2.add(i6);
		
		List<Ingredient> ingr3 = new ArrayList<>();
		ingr3.add(i5);
		ingr3.add(i7);
		ingr3.add(i3);
		ingr3.add(i8);
		ingr3.add(i9);
		
		List<Ingredient> ingr4 = new ArrayList<>();
		ingr4.add(i6);
		ingr4.add(i9);
		ingr4.add(i10);
		
		List<Ingredient> ingr5 = new ArrayList<>();
		ingr5.add(i8);
		ingr5.add(i4);

		ingredientService.save(i1);
		ingredientService.save(i2);
		ingredientService.save(i3);
		ingredientService.save(i4);
		ingredientService.save(i5);
		ingredientService.save(i6);
		ingredientService.save(i7);
		ingredientService.save(i8);
		ingredientService.save(i9);
		ingredientService.save(i10);
		
		//Creazione pizze
		Pizzeria p1 = new Pizzeria("Da Ciccio", "https://garage.pizza/wp-content/uploads/2020/01/DSCF3889-2560x2560.jpeg", 15, "Una delle pizze più buone che ci sia, gustosa e saporita.", pr2, ingr1);
		Pizzeria p2 = new Pizzeria("La più buona", "https://garage.pizza/wp-content/uploads/2020/12/DSCF3442-2560x2560.jpg", 12, "Non si può rifiutare una pizza del genere, solo gusto.", pr2, ingr2);
		Pizzeria p3 = new Pizzeria("Bufalina", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg", 10, "Pura mozzarella di bufala, una delizia.", pr3, ingr3);
		Pizzeria p4 = new Pizzeria("Toccasana", "https://garage.pizza/wp-content/uploads/2020/10/DSCF6160-2560x2560.jpeg", 18, "Un vero e proprio toccasana, restituisce solo dolci sapori.", pr1, ingr4);
		Pizzeria p5 = new Pizzeria("Vegetariana", "https://www.scattidigusto.it/wp-content/uploads/2021/07/pizzeria-50-Kalo-Ciro-Salvo-Napoli-pizza-Nerano.jpg", 13, "Se vuoi mantenerti leggero è la pizza giusta.", pr1, ingr5);
		
		pizzeriaService.save(p1);
		pizzeriaService.save(p2);
		pizzeriaService.save(p3);
		pizzeriaService.save(p4);
		pizzeriaService.save(p5);
		
		//Leggiamo le pizze salvate
		List<Pizzeria> pizze = pizzeriaService.findAll();
		System.out.println(pizze);
		
		//Creo i ruoli
		Role roleUser = new Role("USER");
		Role roleAdmin = new Role("ADMIN");
					
		//Salvo i ruoli
		roleService.save(roleUser);
		roleService.save(roleAdmin);
		
		//Creo una lista di ruoli
		Set<Role> roles = new HashSet<>();
		
		//Aggiungo i ruoli alla lista
		roles.add(roleAdmin);
		roles.add(roleUser);
					
		//Creo gli utenti
		User user = new User("user", "{noop}userpsw", roleUser);
		User admin = new User("admin", "{noop}adminpsw", roleAdmin);
		User userAdmin = new User("userAdmin", "{noop}useradminpsw", roles);
		
		//Salvo gli utenti
		userService.save(user);
		userService.save(admin);
		userService.save(userAdmin);
		
		//Creazione drinks
		Drink d1 = new Drink("Gin-tonic", "https://winedharma.com/wine-dharma/uploads/2020/10/Gin-Tonic-cocktail-ricetta-cocktail-con-gin-e-acqua-tonica.-Gin-tonic-con-lime.jpg", "Una bevanda alcolica, neutra e digeribile.", 8);
		Drink d2 = new Drink("Donperignon", "https://media-verticommnetwork1.netdna-ssl.com/wines/dom-perignon-vintage-492333.jpg", "Una bevanda di alta qualità ottima per un buon aperitivo.", 450);
		Drink d3 = new Drink("Negroni", "https://www.bargiornale.it/wp-content/uploads/sites/4/2018/09/negroni.png", "Una bevanda molto alcolica, poco digeribile.", 10);
	    Drink d4 = new Drink("Spritz", "https://upload.wikimedia.org/wikipedia/commons/0/05/Spritz01.jpg", "Una bevanda leggermente alcolica, per ottimi aperitivi.", 9);
		Drink d5 = new Drink("Coca-cola", "https://www.topbevande.it/images/thumbs/0085545_coca-cola-original-33cl-confezione-da-24-barattoli-lattina_780.jpeg", "Una bevanda gassata e molto buona da bere in qualsiasi momento.", 3);
				
		drinkService.save(d1);
		drinkService.save(d2);
		drinkService.save(d3);
		drinkService.save(d4);
		drinkService.save(d5);
				
		//Leggiamo i nostri drinks
		List<Drink> drinks = drinkService.findAll();
		System.out.println(drinks);
		
		//Delete
		//promotionService.deleteById(1);
		//pizzeriaService.deleteById(1);
		
		System.out.println("------------------------------------------");
		
		List<Pizzeria> pizzas = pizzeriaService.findAll();
		for(Pizzeria pizza : pizzas) {
			System.err.println(pizza.getPromotion());
		}
		
		
		System.out.println("------------------------------------------");
		
        List<Promotion> promotions = promotionService.findAllWithPizza();
		
		for(Promotion promotion : promotions) {
			
			System.err.println(promotion);
			
			for(Pizzeria pizza : promotion.getPizze()) {
				
				System.err.println("\t" + pizza);
			}
		}
		
		System.out.println("------------------------------------------");
		
	}
}
