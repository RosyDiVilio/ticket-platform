package org.lessons.java.ticket.controller;

import java.util.List;

import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private TicketRepository repo;
	
	@GetMapping()
	public String index(Authentication authentication, Model model) {
		
		//prendo i dati da consegnare
		List<Ticket> tickets = repo.findAll();
		//e li inserisco nel modello
		model.addAttribute("tickets", tickets);
		model.addAttribute("username", authentication.getName());
		return "/tickets/home";
	}

}
