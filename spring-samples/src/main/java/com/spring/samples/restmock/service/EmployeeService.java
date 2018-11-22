package com.spring.samples.restmock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.samples.restmock.model.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	private RestTemplate restTemplate;
	
    public Employee getEmployee(long id) {
    ResponseEntity<Employee> resp = 
          restTemplate.getForEntity("http://localhost:8080/employee/" + id, Employee.class);         
    return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
    }

}
