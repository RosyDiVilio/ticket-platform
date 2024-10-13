package org.lessons.java.ticket.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket.model.Ticket;
import org.lessons.java.ticket.model.User;
import org.lessons.java.ticket.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public List<User> findByStatusFalse() {
		return repository.findByStatusFalse();
	}
	
	public List<User> findByStatusTrue() {
		return repository.findByStatusTrue();
	}
	
	public List<User> findByStatusTrueAndRoleUser(String roleName){
		return repository.findByStatusTrueAndRoleName(roleName);
	}
	
	public User getById(Integer Id) {
		return repository.findById(Id).get();
	}
	
	public Optional<User> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public void update(User user) {
		 repository.save(user);
	}

}
