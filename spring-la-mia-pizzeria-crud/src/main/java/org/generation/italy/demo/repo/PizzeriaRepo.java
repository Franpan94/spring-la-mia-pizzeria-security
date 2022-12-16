package org.generation.italy.demo.repo;

import java.util.List;

import org.generation.italy.demo.pojo.Pizzeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzeriaRepo extends JpaRepository<Pizzeria, Integer> {
   
	List<Pizzeria> findByNameContainingIgnoreCase(String name);
}
