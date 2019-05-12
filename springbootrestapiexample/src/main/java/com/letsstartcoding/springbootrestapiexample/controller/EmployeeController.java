package com.letsstartcoding.springbootrestapiexample.controller;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsstartcoding.springbootrestapiexample.dao.EmployeeDAO;
import com.letsstartcoding.springbootrestapiexample.model.Employee;
import com.letsstartcoding.springbootrestapiexample.utility.PagedResult;
import com.letsstartcoding.springbootrestapiexample.utility.Response;




@RestController 
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	
 	public  Response response;
	@Autowired
	EmployeeDAO employeeDAO;
	
	/*save an employee to database*/
	@PostMapping("")
	public ResponseEntity<Response> CreateEmployee(@Valid @RequestBody Employee emp) {
		try 
		{
    		if(emp.getName() == "")
    		{
    			return new ResponseEntity<Response>(new Response(null, System.currentTimeMillis(), 400, "Bad Request", "", "Name is empty", null), HttpStatus.BAD_REQUEST);
    		}
    		else if(emp.getDesignation() == "")
    		{
    			return new ResponseEntity<Response>(new Response(null, System.currentTimeMillis(), 400, "Bad Request", "", "Designation is empty", null), HttpStatus.BAD_REQUEST);
    		}
    		else if(emp.getExpertise() == "")
    		{
    			return new ResponseEntity<Response>(new Response(null, System.currentTimeMillis(), 400, "Bad Request", "", "Expertise is empty", null), HttpStatus.BAD_REQUEST);
    		}
    		
		} 
		catch(TransactionSystemException e)
		{
			return new ResponseEntity<Response>(new Response(null, System.currentTimeMillis(), 400, "Bad Request", "", "Please check your request", null), HttpStatus.BAD_REQUEST);
		}
		Employee data = employeeDAO.save(emp);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).updateEmployee(null));

		return new ResponseEntity<Response>(new Response(data, System.currentTimeMillis(), 200, "", "", "Successfully insert employee", linkTo.withRel("update-employee")),HttpStatus.OK);

	}
	
    /*get all employees*/
	@GetMapping("/{pageNumber}/{limit}/{sort}/{orderBy}")
	public ResponseEntity<Response> getAllEmployees(@PathVariable("pageNumber") int pageNumber, @PathVariable("limit") int limit, @PathVariable("sort") String sort, @PathVariable("orderBy") String orderBy){
		Long totalRecords = employeeDAO.countEmployees(null);
    	int offset = ((pageNumber - 1)  * 10);
    	List<Employee> emp = employeeDAO.fetchAllEmployees(null, offset, limit, sort, orderBy);
        response = new Response(new PagedResult<Employee>(emp, emp.size(), offset, limit, totalRecords, pageNumber), System.currentTimeMillis(), 200, "", "", "Successfully fetch employees", null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	 /*search employees*/
		@PostMapping("/{pageNumber}/{limit}/{sort}/{orderBy}")
		public ResponseEntity<Response> getAllEmployees(@RequestBody(required=false) Employee emp,@PathVariable("pageNumber") int pageNumber, @PathVariable("limit") int limit, @PathVariable("sort") String sort, @PathVariable("orderBy") String orderBy){
			Long totalRecords = employeeDAO.countEmployees(emp);
	    	int offset = ((pageNumber - 1)  * 10);
	    	List<Employee> emps = employeeDAO.fetchAllEmployees(emp, offset, limit, sort, orderBy);
	        response = new Response(new PagedResult<Employee>(emps, emps.size(), offset, limit, totalRecords, pageNumber), System.currentTimeMillis(), 200, "", "", "Successfully search employees", null);
	        return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	
	/*get employee by empId*/
	/*Here we using path variable*/
	@GetMapping("/{id}")
	public ResponseEntity<Response> getemployeeById(@PathVariable(value="id") Long empid){
		Employee emp=employeeDAO.fetchEmployee(empid);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllEmployees(null,0,0,"",""));
    	response = new Response(emp, System.currentTimeMillis(), 200, "", "", "Successfully fetch", linkTo.withRel("all employees"));
    	return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/*update an employee by empid*/
	@Transactional
	@PutMapping("")
	public ResponseEntity<Response> updateEmployee(@RequestBody Employee emp){

		Employee empExist = employeeDAO.fetchEmployee(emp.getId());
		System.out.println("*************");

		System.out.println(empExist);

		if (empExist == null)
    	{
    		System.out.println("*************");
    		System.out.println(empExist);
    		return ResponseEntity.notFound().build();
    	}
    	else {
    	employeeDAO.updateEmployee(emp);
    	return ResponseEntity.noContent().build();
    	}
    }	
	/*delete an employee*/
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteEmployee(@PathVariable (value="id") Long empid){
		Employee emp=employeeDAO.fetchEmployee(empid);
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		employeeDAO.delete(emp);
        return new ResponseEntity<Response>(new Response(null, System.currentTimeMillis(), 200, "", "", "SuccessFully Deleted", null), HttpStatus.OK);
	}
	
}
