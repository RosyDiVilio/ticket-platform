package org.lessons.java.ticket.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket.model.Note;
import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.repo.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
	
	@Autowired
	private NoteRepository repository;
	
	public List<Note> findAll() {
		return repository.findAll();
	}
	
	public Note getById(Integer id) {
		return repository.findById(id).get();
	}
	
	public Note create(Note note) {
		return repository.save(note);
	}
	
	public List<Note> findByTicketId(Integer id){
		return repository.findByTicketId(id);
	}


}
