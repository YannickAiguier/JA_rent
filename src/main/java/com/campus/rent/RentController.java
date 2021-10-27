package com.campus.rent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// annotation swagger pour personnaliser le message de descritpion du controller
@Api(description = "API pour les opérations CRUD sur les modèles de voitures")
//annotation qui combine @Controller (gère les requêtes http) et @ResponseBody (lie la valeur de retour au corps de la réponse http, convertit l'objet renvoyé en JSON) 
@Controller
public class RentController {

	@Autowired
	private RestTemplate restTemplate;

	// annotation swagger pour personnaliser la description du end-point
	@ApiOperation(value = "Affiche la liste des modèles de voitures")
	// indique que la méthode répondra à une requête GET
	// équivalent à @RequestMapping(method=RequestMethod.GET,value=...)
	@GetMapping(value = { "/" })
	public String index() {
		return "index";
	}

	@GetMapping(value = { "/cars" })
	public String list(Model model) {
		// appel de l'API cars pour récupérer la liste des voitures
		CarList myCars = new CarList();
		myCars.setCarList(restTemplate.getForObject("http://localhost:8081/modeles", List.class));

		// appel de la vue avec la liste en paramètre
		model.addAttribute("cars", myCars.getCarList());
		return "carsList";
	}

	@ApiOperation(value = "Affiche les caractéristiques d'une voiture d'après son Id")
	// indique que la méthode répondra à une requête GET
	@GetMapping(value = "/cars/{id}")
	public String show(@PathVariable int id, Model model) {
		// appel de l'API cars pour récupérer la voiture
		Car car = restTemplate.getForObject("http://localhost:8081/modeles/" + id, Car.class);

		// appel de la vue avec la voiture en paramètre
		model.addAttribute("car", car);
		return "car";
	}

	@GetMapping(value = { "/addCar" })
	public String showAddCarPage(Model model) {
		CarForm carForm = new CarForm();
		model.addAttribute("carForm", carForm);
		return "addCar";
	}

	@PostMapping(value = { "/addCar" })
	public String saveCar(Model model, @ModelAttribute("carForm") CarForm carForm) {

		String modele = carForm.getModele();
		String marque = carForm.getMarque();
		String couleur = carForm.getCouleur();

		if (modele != null && modele.length() > 0 //
				&& marque != null && marque.length() > 0 && couleur != null && couleur.length() > 0) {
			Car newCar = new Car();
			newCar.setMarque(marque);
			newCar.setModele(modele);
			newCar.setCouleur(couleur);
			
			// appel API pour création
			restTemplate.postForObject("http://localhost:8081/modeles", newCar, String.class);

			return "redirect:/cars";
		}

		model.addAttribute("errorMessage", "Modèle, Marque et Couleur requis !!");
		return "addCar";
	}

	@GetMapping(value = { "/editCar/{id}" })
	public String showEditCarPage(@PathVariable int id, Model model) {
		editCarForm editCarForm = new editCarForm();
		Car tmpCar = restTemplate.getForObject("http://localhost:8081/modeles/" + id, Car.class);
		editCarForm.setId(String.valueOf(id));
		editCarForm.setMarque(tmpCar.getMarque());
		editCarForm.setModele(tmpCar.getModele());
		editCarForm.setCouleur(tmpCar.getCouleur());
		model.addAttribute("editCarForm", editCarForm);
		return "editCar";
	}

	@PostMapping(value = { "/editCar" })
	public String saveEditedCar(Model model, @ModelAttribute("editCarForm") editCarForm carForm) {

		String id = carForm.getId();
		String modele = carForm.getModele();
		String marque = carForm.getMarque();
		String couleur = carForm.getCouleur();

		if (modele != null && modele.length() > 0 //
				&& marque != null && marque.length() > 0 && couleur != null && couleur.length() > 0) {
			Car newCar = new Car();
			newCar.setMarque(marque);
			newCar.setModele(modele);
			newCar.setCouleur(couleur);
			
			restTemplate.postForObject("http://localhost:8081/modeles", newCar, String.class);
			return "redirect:/cars";
		}

		model.addAttribute("errorMessage", "Modèle, Marque et Couleur requis !!");
		return "addCar";
	}

	@PostMapping(value = "/deleteCar")
	public String deleteCar(@RequestParam("carId") String carId) {
		myCars.delete(Integer.parseInt(carId));
		return "redirect:/cars";
	}

}
