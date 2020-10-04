package com.customer.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customer.ms.dao.CustomerDAO;
import com.customer.ms.model.Customer;
import com.customer.ms.model.CustomerM;
import com.customer.ms.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerDAO customerDAO;

	@RequestMapping("/hello")
	public String hello() {
		return "Greetings from Spring Boot Application 1.0";
	}

	// URL - http://localhost:8080/customers
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Customer> getCustomers() {
		List<Customer> list = customerDAO.getAllCustomers();
		return list;
	}

	// URL - http://localhost:8080/customer/{cusId}
	@RequestMapping(value = "/customer/{cusId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Customer getCustomer(@PathVariable("cusId") String cusId) {
		return customerDAO.getCustomer(cusId);
	}

	// URL - POST http://localhost:8080/customer}
	@RequestMapping(value = "/customer", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Customer addCustomer(@RequestBody Customer customer) {
		return customerDAO.addCustomer(customer);
	}

	// URL - PUT http://localhost:8080/customer}
	@RequestMapping(value = "/customer", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Customer updateCustomer(@RequestBody Customer customer) {
		return customerDAO.updateCustomer(customer);
	}

	// URL - DELETE http://localhost:8080/customer/{cusId}
	@RequestMapping(value = "/customer/{cusId}", method = RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable("cusId") String cusId) {
		customerDAO.deleteCustomer(cusId);
	}
	
	@Autowired
	private CustomerService customerService;
	
	// URL - http://localhost:8080/mongoCustomers
	@RequestMapping(value = "/mongoCustomers", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<CustomerM> getMongoCustomers() {
		List<CustomerM> list = customerService.findAll();
		return list;
	}

	// URL - http://localhost:8080/mongoCustomer/{cusId}
	@RequestMapping(value = "/mongoCustomer/{cusId}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public CustomerM getMongoCustomer(@PathVariable("cusId") String cusId) {
		return customerService.findById(cusId);
	}
	
	// URL - POST http://localhost:8080/mongoCustomer}
	@RequestMapping(value = "/mongoCustomer", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public CustomerM addMongoCustomer(@RequestBody CustomerM customerM) {
		return customerService.addCustomer(customerM);
	}
	
	// URL - PUT http://localhost:8080/mongoCustomer}
	@RequestMapping(value = "/mongoCustomer", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public CustomerM updateMongoCustomer(@RequestBody CustomerM customerM) {
		return customerService.updateCustomer(customerM);
	}
	
	// URL - DELETE http://localhost:8080/mongoCustomer/{cusId}
	@RequestMapping(value = "/mongoCustomer/{cusId}", method = RequestMethod.DELETE)
	public void deleteMongoCustomer(@PathVariable("cusId") String cusId) {
		customerService.deleteCustomer(cusId);
	}

}