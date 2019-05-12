package com.letsstartcoding.springbootrestapiexample.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.letsstartcoding.springbootrestapiexample.model.Employee;
import com.letsstartcoding.springbootrestapiexample.repository.EmployeeRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Service
public class EmployeeDAO {

	private static Logger logger;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	EmployeeRepository employeeRepository;

//	save an employee

	public Employee save(Employee emp) {
		return employeeRepository.save(emp);
	}
	
//  search all employees
	
	public List<Employee> findAllEmployees(){
		return employeeRepository.findAll();
	}
	
	
//	get employee by id
	
	public Employee fetchEmployee(Long empid) {
		return this.fetchColumnByParam(" e ", " Employee e ", " ", " WHERE e.id = "+empid);
	}
	
// Delete employee by id
	
	public void delete(Employee emp) {
		employeeRepository.delete(emp);
	}
	
// Update employee
	
	public void updateEmployee(Employee emp) 
	{	
		this.update(emp);
	}
	
	
//	count employees
	
	public Long countEmployees(Employee emp) 
	{
		String where = " WHERE 1=1 ";
		String entity = " "; 
		if(emp != null)
		{
			if(emp.getName() != null && emp.getName() != "")
			{
				where += " and e.name = '"+emp.getName()+"'";
			}
			
			if(emp.getDesignation() != null && emp.getDesignation() != "")
			{
				where += " and e.Designation = '"+emp.getDesignation()+"'";
			}
			
			if(emp.getExpertise() != null && emp.getExpertise() != "")
			{
				where += " and e.Expertise = '"+emp.getExpertise()+"'";
			}
			
			
		}
		String sql = " SELECT COUNT(e) FROM Employee e "+entity+where;
		return this.fetchQuery(sql);
	}
	
	public <T> T fetchQuery(String query)
	{
		logger = this.getLoggerInstance(CrudRepository.class);
		T result = null;
		try
		{
			result = (T) entityManager.createQuery(query).setMaxResults(1).getSingleResult(); 
		} 
		catch (NullPointerException e) 
		{
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public <T> Logger getLoggerInstance(T t)
	{
		return LogManager.getLogger(t.getClass().getName());
	}
	
//	fetch all employees with pagination
	
	public List<Employee> fetchAllEmployees(Employee emp, int offset, int limit, String sort, String orderBy)
	{	
		if(sort.equals("id"))
		{
			sort = " e.id ";
		}
		
		String where = " WHERE 1=1 ";
		String entity = " "; 
		if(emp != null)
		{
			if(emp.getName() != null && emp.getName() != "")
			{
				where += " and e.name = '"+emp.getName()+"'";
			}
			
			if(emp.getDesignation() != null && emp.getDesignation() != "")
			{
				where += " and e.Designation = '"+emp.getDesignation()+"'";
			}
			
			if(emp.getExpertise() != null && emp.getExpertise() != "")
			{
				where += " and e.Expertise = '"+emp.getExpertise()+"'";
			}
		}
		return this.fetchColumnsByParam(" e ", " Employee e " + entity, "", where, offset, limit, sort, orderBy);
	} 
	
	@SuppressWarnings("unchecked")
	public <T> List<T> fetchColumnsByParam(String column, String tableName, String join, String condition, int offset, int limit, String sort, String orderBy)
	{
		logger = this.getLoggerInstance(CrudRepository.class);
		List<T> result = null;
		
		limit = (limit != 0 ? limit : 10);
    	offset = (offset != 0 ? offset : 0);
    	sort = (sort != "" ? " ORDER BY " + sort + " " +(orderBy != "" ? orderBy : "DESC") : "");
    	
		try
		{
			result = (List<T>) entityManager.createQuery("SELECT "+column+" FROM " + tableName + join + condition + " "+sort).setFirstResult(offset).setMaxResults(limit).getResultList();
		} 
		catch (NullPointerException e) 
		{
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public <T> T fetchColumnByParam(String column, String tableName, String join, String condition)
	{
		logger = this.getLoggerInstance(CrudRepository.class);
		T result = null;
		try
		{
			result = (T) entityManager.createQuery("SELECT "+column+" FROM " + tableName + join + condition).setMaxResults(1).getSingleResult();
		} 
		catch (NullPointerException e) 
		{
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public <T> T update(T t) 
	{	
		logger = this.getLoggerInstance(t);
		try 
		{	
			entityManager.merge(t); 
		}
		catch (Exception e) 
		{
			logger.error(e.getMessage());
			e.getMessage(); 
		}
		return t;
	}





	
	
	
}
