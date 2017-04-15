package test.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import test.dao.TestDao;
import test.entity.Person;
import test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
	
	@Resource
	TestDao testDao;

	@Override
	public void say() {
		System.out.println("service saying hi.");
	}

	@Override
	public void save(Person person) {
		testDao.save(person);
		int i = 1/0;
	}

	@Override
	public Person findPerson(Serializable id) {
		save(new Person("test"));
		return testDao.findPerson(id);
	}

}
