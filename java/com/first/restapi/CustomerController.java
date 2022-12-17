package com.first.restapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	
	@Autowired
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping("api/v1/customers")
	public List<Customer> getCustomers(
			@RequestParam(required=false) String searchName, 
			@RequestParam(required=false) String searchEmail,
			@RequestParam(required=false) String searchAge,
			@RequestParam(required=false) String orderPropertyName
			){
		return customerService.getCustomers(searchName, searchEmail, searchAge, orderPropertyName);
	}
	
	@GetMapping("api/v1/customer/{id}")
	public Optional<Customer> getCustomerById(@PathVariable Integer id){
		return customerService.getCustomerById(id);
	}
	
	@PostMapping("api/v1/customer")
	public void addCustomer(@RequestBody Customer customer) {
		customerService.addCustomer(customer);
	}
	
	@DeleteMapping("api/v1/customer/{id}")
	public void deleteCustomerById(@PathVariable Integer id) {
		customerService.deleteCustomerById(id);
	}
	
	@PutMapping("api/v1/customer/{id}")
	public void updateCustomer(@PathVariable Integer id, 
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) String email) {
		customerService.updateCustomer(id, name, email);
	}
}
