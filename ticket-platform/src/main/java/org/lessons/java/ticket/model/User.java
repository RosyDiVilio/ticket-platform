package org.lessons.java.ticket.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {
	
	@Id
	private Integer id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	private boolean status;

	//prende tutti i ruoli non appena prende l'utente
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;
	
	//relazione definita nello user
	@OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
	@JsonManagedReference
	private List<Ticket> tickets;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<Note> notes;
	
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
