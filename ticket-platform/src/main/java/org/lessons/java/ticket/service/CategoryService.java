package org.lessons.java.ticket.service;

import java.util.List;

import org.lessons.java.ticket.model.Category;
import org.lessons.java.ticket.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category findByName(String name) {
		return repository.findByName(name);
	}

}
