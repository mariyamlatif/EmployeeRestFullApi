package com.letsstartcoding.springbootrestapiexample.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.letsstartcoding.springbootrestapiexample.model.Employee;
import com.letsstartcoding.springbootrestapiexample.repository.EmployeeRepository;


public class EmployeeValidation {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	
}
