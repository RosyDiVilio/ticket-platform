package org.lessons.java.ticket.repo;

import org.lessons.java.ticket.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//fornisce metodi per eseguire operazioni crud
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category findByName(String name);
	
	//in automatico ho tutto il necessario, ma posso aggiungere altri metodi

}
