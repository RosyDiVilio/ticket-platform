package org.lessons.java.ticket.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.service.CategoryService;
import org.lessons.java.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//strumento per gestire la risorsa ticket che rispetta direttiva rest
@RestController
@CrossOrigin //chiunque vi può accedere
@RequestMapping("api/tickets")
public class TicketRestController {
	
	@Autowired
	private TicketService service;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/titolo")
	public List<Ticket> index(@RequestParam(name= "word", required= false) String word){
		
		List<Ticket> result;
		
		if(word != null && !word.isEmpty()) {
			result = service.findAllByTitle(word);
		} else {
			result = service.findAllSortedByRecent();
		}
		 
		return result;
	}
	
	@GetMapping("/stato")
	public List<Ticket> stato(@RequestParam(name= "stato", required= true) String stato){
		
		List<Ticket> result = new ArrayList<>();
		
		if(stato != null && !stato.isEmpty()) {
			result = service.findAllByStatus(stato);
		} else {
			result = service.findAllSortedByRecent();
		}
		 
		return result;
	}
	
	@GetMapping("/categoria")
	public List<Ticket> categoria(@RequestParam(name = "categoria", required = true) String categoria) {
		
		return categoryService.findByName(categoria).getTickets();

   }
}
