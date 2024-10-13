package org.lessons.java.ticket.repo;

import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//fornisce metodi per eseguire operazioni crud (eredita una serie di metodi che ci permette di gestire le richieste
//al database per recuperare/salvare i dati
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//in automatico ho tutto il necessario, ma posso aggiungere altri metodi
	
	//metodo che mi trova l'utente se c'è(optional)
	public Optional<User> findByUsername(String username);
	
	public List<User> findByStatusFalse();
	public List<User> findByStatusTrue();
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE u.status = true AND r.name = :roleName")
    List<User> findByStatusTrueAndRoleName(@Param("roleName") String roleName);
	
	public Optional<User> findById(Integer id);
	
	public User save(User user);

}
