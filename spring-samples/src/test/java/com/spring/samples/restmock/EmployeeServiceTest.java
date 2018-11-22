package com.spring.samples.restmock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.spring.samples.restmock.model.Employee;
import com.spring.samples.restmock.service.EmployeeService;

/**
 * simple JUnit test using mocks for rest template 
 * @author carlosdlr
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private EmployeeService employeeService;

    @Test
	public void givenMockingIsDoneByMockitoWhenGetIsCalled() {
		Employee employee = new Employee(123, "Carlos De La Rosa");
		when(restTemplate.getForEntity("http://localhost:8080/employee/123", Employee.class))
			.thenReturn(new ResponseEntity(employee, HttpStatus.OK));

		Employee employeeResponse = employeeService.getEmployee(123);
		assertEquals(employeeResponse, employee);
	}
}
