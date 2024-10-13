package org.lessons.java.ticket.service;

import java.util.List;

import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.model.User;
import org.lessons.java.ticket.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository repo;
	
	public List<Ticket> findAllSortedByRecent() {
		return repo.findAll(Sort.by("createdAt"));
	}
	
	public List<Ticket> findAllByTitle(String title) {
		return repo.findByTitleContains(title);
	}
	
	public Ticket getById(Integer id) {
		return repo.findById(id).get();
	}
	
	public Ticket create(Ticket ticket) {
		return repo.save(ticket);
	}
	
	public Ticket update(Ticket ticket) {
		return repo.save(ticket);
	}
	
	public void deleteById(Integer id) {
	    repo.deleteById(id);
	}
	
	public List<Ticket> findAllByStatus(String stato) {
	    return repo.findAllByStatus(stato);
	}
	
	public List<Ticket> findAllByUsername(String username) {
		return repo.findAllByUsername(username);
	}
	
}
