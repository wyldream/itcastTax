package test.dao;

import java.io.Serializable;

import test.entity.Person;

public interface TestDao {

	
	public void save(Person person);
	
	public Person findPerson(Serializable id);

}
