package org.lessons.java.ticket.controller;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket.model.Note;
import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.model.User;
import org.lessons.java.ticket.repo.UserRepository;
import org.lessons.java.ticket.service.NoteService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserService userService;
	
	    //INDEX
	    @GetMapping("/indexNote/{ticketId}")
	    public String index (Model model) {
		
		    model.addAttribute("note", noteService.findAll());
		    return "/notes/indexNote";
	    }
		
	    //SHOW
		@GetMapping("/showNote/{id}")
		public String show(@PathVariable("id") Integer id, Model model) {
			Ticket ticket = ticketService.getById(id);
			List<Note> notes = noteService.findByTicketId(ticket.getId());
			model.addAttribute("note", notes);
			model.addAttribute("ticket", ticket);
			return "notes/showNote";
		}
		
		
		//CREATE
		@GetMapping("/createNote/{ticketId}")
		public String create(@PathVariable Integer ticketId, Model model, Authentication authentication) {
		    Ticket ticket = ticketService.getById(ticketId);
		    User user = userService.findByUsername(authentication.getName()).get();
		    Note note = new Note();
		    note.setUser(user); // Associa l'utente alla nota
		    note.setTicket(ticket);
		    
		    
		    model.addAttribute("note", note);
		    model.addAttribute("ticket", ticket);
		    return "notes/createNote";
		}
		
		
		
		//STORE
		@PostMapping("/createNote/{ticketId}")
		public String store(@PathVariable Integer ticketId, @ModelAttribute Note note, 
		                    BindingResult bindingResult,
		                    Model model) {
		    if(bindingResult.hasErrors()) {
		        model.addAttribute("ticket", ticketService.getById(ticketId)); // Ricarica il ticket
		        return "notes/createNote"; 
		    }

		    note.setNoteCreatedAt(new Timestamp(System.currentTimeMillis())); // Imposta la data di creazione
		    note.setTicket(ticketService.getById(ticketId)); // Associa il ticket
		    
		    noteService.create(note);

		    return "redirect:/tickets/" + note.getTicket().getId();
		}
				

}
