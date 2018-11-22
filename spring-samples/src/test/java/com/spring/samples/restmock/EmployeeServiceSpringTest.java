package com.spring.samples.restmock;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.samples.SpringSamplesApplication;
import com.spring.samples.restmock.model.Employee;
import com.spring.samples.restmock.service.EmployeeService;

/**
 * spring test using mock rest service server for rest template 
 * @author carlosdlr
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringSamplesApplication.class)
public class EmployeeServiceSpringTest {

    @Autowired
    private EmployeeService empService;
    @Autowired
    private RestTemplate restTemplate;
 
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
    
    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
	public void givenMockingIsDoneByMockitoWhenGetIsCalled() throws URISyntaxException, JsonProcessingException {
        Employee emp = new Employee(123, "Carlos De La Rosa");
        mockServer.expect(ExpectedCount.once(), 
          requestTo(new URI("http://localhost:8080/employee/123")))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.OK)
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(emp))
        );                                   
                        
        Employee employee = empService.getEmployee(123);
        mockServer.verify();
        assertEquals(emp.toString(), employee.toString());    
	}
}
