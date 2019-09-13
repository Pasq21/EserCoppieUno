package it.lefosse.controller;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lefosse.connection.GestioneDb;

@Controller
public class WelcomeController {
	
    @GetMapping("/")
    public String main(Model model) throws ClassNotFoundException, SQLException {
    	List<String> lista =new ArrayList<String>();
    	lista.addAll(GestioneDb.getContinent());
    	model.addAttribute("lista", lista);
        return "intro"; 
    }
    
    @GetMapping("/scelta")
    public String hello(
    		@RequestParam(name = "continente") String continente, Model model)throws ClassNotFoundException, SQLException {
    			List<String> stati= new ArrayList<String>();
    			stati.addAll(GestioneDb.getStati(continente));
    			model.addAttribute("stati", stati);
				return "scelta";
}
    
    @GetMapping("/sceltadue")
    public String hellodue(
    		@RequestParam(name = "stato") String stato, Model model)throws ClassNotFoundException, SQLException {
				return "sceltadue";
    }
}