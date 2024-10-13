package org.lessons.java.ticket.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.lessons.java.ticket.model.Role;
import org.lessons.java.ticket.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DatabaseUserDetails implements UserDetails {
	
	private final Integer id;
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;
	
	public DatabaseUserDetails(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		
		authorities = new HashSet<GrantedAuthority>();
		
		//per ogni ruolo che si chiami ruolo, che si trova nei ruoli presenti in utente 
	    for (Role role : user.getRoles()) {
		//lo aggiungi all'hashset (per ogni ruolo che ha l'utente aggiungi un authorities)	
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {	
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	//metodi aggiuntivi
	
	//account non scaduto
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
		
	//account non bloccato
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
			
	//credenziali non scadute
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
			
	//account abilitato
	@Override
	public boolean isEnabled() {
		return true;
	}
		

}
