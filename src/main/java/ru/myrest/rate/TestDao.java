package ru.myrest.rate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TestDao {

	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  public void createUser(String name, int age) {
		  Person person = new Person();
		  person.setName(name);
		  person.setAge(age);
		  entityManager.persist(person);
	}
}
