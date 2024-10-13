package org.lessons.java.ticket.security;

import java.util.Optional;

import org.lessons.java.ticket.model.User;
import org.lessons.java.ticket.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//recuperare informazioni che fanno riferimento a user
@Service
public class DatabaseUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	//UserDetailsService offre questo metodo che prende l'untente dall'username e manda un'eccezione se non lo trova
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//cerca nel DB un utente che abbia lo username richiesto
		Optional<User> user = userRepository.findByUsername(username);
		
		//se lo trova allora crea una nuova istanza di DatabaseUserDetails che riguarda l'utente con lo username inserito
		if(user.isPresent()) {
			return new DatabaseUserDetails(user.get());
		//se non lo trova lancia eccezione
		} else throw new UsernameNotFoundException("username non trovato");
	}

}
