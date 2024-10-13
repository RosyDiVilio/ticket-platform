package org.lessons.java.ticket.model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Size(min=3, max=250)
	@Column(name ="title", nullable = false)
	private String title;
	
	@NotBlank
	@Size(min=5, max=250)
	@Column(name ="description", nullable = false)
	private String description;
	
	@NotNull
	@Column(name ="status", nullable = false)
	private String status;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	private Timestamp updatedAt;
	
	@Transient
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	@ManyToMany()
	@JoinTable(
			name = "ticket_category",  //nome tabella ponte
			joinColumns = @JoinColumn(name = "ticket_id"),  //nome chiave esterna ticket
			inverseJoinColumns = @JoinColumn(name = "category_id")  //nome chiave esterna category
			)
	@JsonBackReference
	private List<Category> categories;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Note> notes;

	
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	} 
	
	public String getFormattedCreatedAt() {
		if (createdAt != null) {
			return createdAt.toLocalDateTime().format(dateFormatter);
		}
		return null;
	}
	
	public String getFormattedUpdatedAt() {
		if (updatedAt != null) {
			return updatedAt.toLocalDateTime().format(dateFormatter);
		}
		return null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
