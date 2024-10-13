package org.lessons.java.ticket.model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String text;
	
	@NotNull
	private Timestamp noteCreatedAt;
	
	@Transient
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	public String getFormattedNoteCreatedAt() {
		if (noteCreatedAt != null) {
			return noteCreatedAt.toLocalDateTime().format(dateFormatter);
		}
		return null;
	}
	
	@ManyToOne		
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	@JsonBackReference
	private Ticket ticket;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket tickets) {
		this.ticket = tickets;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getNoteCreatedAt() {
		return noteCreatedAt;
	}

	public void setNoteCreatedAt(Timestamp noteCreatedAt) {
		this.noteCreatedAt = noteCreatedAt;
	}

}
