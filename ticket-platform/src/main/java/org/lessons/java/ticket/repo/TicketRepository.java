package org.lessons.java.ticket.repo;

import java.util.List;

import org.lessons.java.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//fornisce metodi per eseguire operazioni crud (eredita una serie di metodi che ci permette di gestire le richieste
//al database per recuperare/salvare i dati
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	//in automatico ho tutto il necessario, ma posso aggiungere altri metodi
	public List<Ticket> findByTitle(String title);
	
	public List<Ticket> findByTitleContains(String title);

	public List<Ticket> findAllByStatus(String stato);
	
	@Query("select t from Ticket t where t.user.username = :username")
	public List<Ticket> findAllByUsername(@Param("username") String username);

}
