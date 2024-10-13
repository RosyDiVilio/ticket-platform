package org.lessons.java.ticket.controller;

import java.util.List;

import org.lessons.java.ticket.model.Category;
import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.model.User;
import org.lessons.java.ticket.service.CategoryService;
import org.lessons.java.ticket.service.TicketService;
import org.lessons.java.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

//controller = prende dati dal model e li rendere disponibili alla view
@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	//autowired per D.I.
	@Autowired
	private TicketService service;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired 
	private UserService userService;
	
	@GetMapping()
	public String index(Authentication authentication, Model model, @RequestParam(name = "title", required = false) String title) {
		
		List<Ticket> tickets;
		
		//prendo i dati da consegnare
		if (title != null && !title.isEmpty()) {
			tickets = service.findAllByTitle(title);
		}
		else {
	        tickets = service.findAllSortedByRecent();
	}
		
		//e li inserisco nel modello
		model.addAttribute("tickets", tickets);
		model.addAttribute("username", authentication.getName());
		return "/tickets/index";
	}
	
	@GetMapping("/findByTitle/{title}")
	public String findByTitle(@PathVariable("title") String title, Model model) {
		model.addAttribute("tickets", service.findAllByTitle(title));
		return "tickets/index";
	}
	
	//SHOW
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("ticket", service.getById(id));
		return "tickets/show";
	}
	
	//CREATE
	@GetMapping("/create")
	public String create(Model model) {
		List<User> users = userService.findByStatusTrueAndRoleUser("USER");
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("users", users);
		return "tickets/create";
	}
	
	//STORE
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, 
			             BindingResult bindingResult,
			             Model model, 
			             RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAll());
			model.addAttribute("users", userService.findByStatusTrueAndRoleUser("USER"));
			return "/tickets/create";
		} 
		
		service.create(formTicket);
		
		attributes.addFlashAttribute("successMessage", formTicket.getTitle() + " è stato creato con successo");
		
		return "redirect:/tickets";
		
	}
	
	//EDIT una GET che restituisce un form con i dati della risorsa richiesta
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
	//List<User> users = userService.findByStatusTrueAndRoleUser("USER");
		//prendo il libro e lo do al modello
		model.addAttribute("ticket", service.getById(id));
		model.addAttribute("categories", categoryService.findAll());
		//model.addAttribute("user", users);
		model.addAttribute("user", service.getById(id).getUser());
		
		//restiruisco la view con il model inserito
		return "/tickets/edit";
	}
	
	//UPDATE una POST che aggiorna i dati della risorsa in esame nel database
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket updateFormTicket, 
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAll());
			model.addAttribute("users", userService.findByStatusTrueAndRoleUser("USER"));
			return "/tickets/edit";
		} 
		
		attributes.addFlashAttribute("successMessage", updateFormTicket.getTitle() + " è stato modificato con successo");
		
		service.update(updateFormTicket);
		return "redirect:/tickets";
	}
	
	//DELETE
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		
		service.deleteById(id);
		
		attributes.addFlashAttribute("successMessage", "Il ticket con id " + id + " è stato cancellato con successo");
		
		return "redirect:/tickets";	
	}

}
