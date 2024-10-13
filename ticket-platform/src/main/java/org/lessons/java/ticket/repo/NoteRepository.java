package org.lessons.java.ticket.repo;

import java.util.List;

import org.lessons.java.ticket.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
//fornisce metodi per eseguire operazioni crud
public interface NoteRepository extends JpaRepository<Note, Integer> {
	
	//in automatico ho tutto il necessario, ma posso aggiungere altri metodi
	
	public List<Note> findByTicketId(Integer id);

}
