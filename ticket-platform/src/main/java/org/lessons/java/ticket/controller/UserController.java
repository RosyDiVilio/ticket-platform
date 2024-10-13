package org.lessons.java.ticket.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.model.User;
import org.lessons.java.ticket.repo.TicketRepository;
import org.lessons.java.ticket.service.TicketService;
import org.lessons.java.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operators")
public class UserController {
	
	@Autowired
	private TicketService service;
	
	@Autowired 
	private UserService userService;
	
	@GetMapping()
	public String index(Authentication authentication, Model model) {
		
		//INDEX
		//prendo i dati da consegnare
		Optional<User> user = userService.findByUsername(authentication.getName());
		List<Ticket> tickets = service.findAllByUsername(user.get().getUsername());
		
		String stato;
		
		if(user.get().getStatus() == true) {
			stato = "Attivo";
		} else {
			stato = "Non Attivo";
		}
		//e li inserisco nel modello
		model.addAttribute("tickets", tickets);
		model.addAttribute("username", user);
		model.addAttribute("userId",user.get().getId());
		model.addAttribute("status", stato);
		return "/operators/indexOperator";
	}
	
	//EDIT
	@GetMapping("/editStatusUser/{id}")
	public String editStatusUser(@PathVariable("id") Integer id, Authentication authentication, Model model) {
		
		model.addAttribute("user", userService.getById(id));
		
		return "/operators/editStatusUser";
	}
	
	//UPDATE
	@PostMapping("/editStatusUser/{id}")
	public String editUserStatus(@PathVariable("id") Integer id, @RequestParam("status") Boolean status, RedirectAttributes attributes) {
		User user = userService.getById(id);
		
		List<Ticket> tickets = service.findAllByUsername(user.getUsername());
		
		for(Ticket ticket : tickets) {
			if(ticket.getStatus().equals("da fare") || ticket.getStatus().equals("in corso")) {
				attributes.addFlashAttribute("errorMessage", "Hai ancora ticket da lavorare");
				return "redirect:/operators";
			}
		}
		
		user.setStatus(status);
		userService.update(user);
		
		return "redirect:/operators";
	}
	
	@GetMapping("/editStatusTicket/{id}")
	public String editTicketStatus(@PathVariable("id") Integer id, Model model, Authentication autentication) {
		
		 Ticket ticket = service.getById(id);
		 Optional<User> user = userService.findByUsername(autentication.getName());
		 if(ticket.getUser().getUsername().equals(autentication.getName())) {
			 model.addAttribute("ticket", ticket);
			 return "/operators/editStatusTicket";
		 } 
		 
		 return "redirect:/operators";
	}
	
	@PostMapping("/editStatusTicket/{id}")
	public String editTicketStatus(@PathVariable("id") Integer id, Model model, 
			                       @RequestParam("status") String status, 
			                       Authentication autentication) {
		 
		Ticket ticket = service.getById(id);
		ticket.setStatus(status);
		service.update(ticket);
		
		return "redirect:/operators";
	}
	
}
