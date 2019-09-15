package it.lefosse.controller;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lefosse.connection.GestioneDb;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String main(Model model) throws ClassNotFoundException, SQLException {
		List<String> lista = new ArrayList<String>();
		lista.addAll(GestioneDb.getContinent());
		model.addAttribute("lista", lista);
		return "intro";
	}

	@GetMapping("/scelta")
	public String hello(@RequestParam(name = "continente") String continente, Model model)
			throws ClassNotFoundException, SQLException {
		List<String> stati = new ArrayList<String>();
		stati.addAll(GestioneDb.getStati(continente));
		model.addAttribute("stati", stati);
		return "scelta";
	}

	@GetMapping("/sceltadue")
    public String hellodue(
    		@RequestParam(name = "stato") String stato, Model model)throws ClassNotFoundException, SQLException {
    	List<String> citta=new ArrayList<String>();
    	citta.addAll(GestioneDb.getCitta(stato));
    
    	model.addAttribute("stato", stato);
    	model.addAttribute("listaCitta", citta);
				return "sceltadue";
    }

	@PostMapping("/inserimento")
	public String hellotre(
			@RequestParam(name = "stato") String stato,
			@RequestParam(name = "cittaInserita") String cittaInserita,
			@RequestParam(name = "distrettoInserito") String distretto, Model model) throws ClassNotFoundException, SQLException {
			String countryCode=GestioneDb.getCode(stato);
			int popolazione=GestioneDb.randomPop(stato,countryCode);
			GestioneDb.insertCitta(cittaInserita, countryCode, distretto, popolazione);
			
		return "inserimento";
	}

}