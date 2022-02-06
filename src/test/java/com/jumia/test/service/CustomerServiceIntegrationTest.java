package com.jumia.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jumia.controller.rest.CustomerController;
import com.jumia.db.model.CustomerView;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceIntegrationTest {
	
	
	CustomerController controller;

	
	@Test
	public void getAllCustomersWithoutFilter() {
		controller = new CustomerController();
		ResponseEntity<PagedResources<CustomerView>> response = controller.getAllCustomers(0, 10, "id", "asc", null, null);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getContent().size(), 10);
		assertEquals(response.getBody().getMetadata().getTotalPages(),5);
		assertEquals(response.getBody().getMetadata().getTotalElements(),41);
		assertEquals(response.getBody().getMetadata().getSize(),10);
		assertEquals(response.getBody().getLinks().size(),4);
		
	}

}
