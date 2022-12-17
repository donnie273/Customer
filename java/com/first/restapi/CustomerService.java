package com.first.restapi;

import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
	
	@Autowired
	private final CustomerRepository customerRepository;
	@Autowired
	private final HibernateCriteria hibernateCriteria;
	
	public CustomerService(CustomerRepository customerRepository,HibernateCriteria hibernateCriteria) {
		this.customerRepository = customerRepository;
		this.hibernateCriteria = hibernateCriteria;
	}
	
	public List<Customer> getCustomers(
			String searchName, String searchEmail, String searchAge, String order){
		
		if(searchName != null && !searchName.isEmpty())
			return hibernateCriteria.filter(hibernateCriteria.EQ_FRONT, "name", searchName);
		if(searchEmail != null && !searchEmail.isEmpty())
			return hibernateCriteria.filter(hibernateCriteria.EQ_FRONT, "email", searchEmail);
		if(searchAge != null && !searchAge.isEmpty())
			return hibernateCriteria.filter(hibernateCriteria.GT, "age", searchAge);
		if(order != null && !order.isEmpty())
			return hibernateCriteria.order(hibernateCriteria.ASC, order);
		
		return hibernateCriteria.getResult();
	}
	
	public Optional<Customer> getCustomerById(Integer id){
		return customerRepository.findById(id);
	}
	
	public void addCustomer(Customer customer) {
		Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());
		if(customerOptional.isPresent())
			throw new CustomerEmailAlreadyExists("email exists");
		if(!customer.getEmail().contains("@"))
			throw new IllegalArgumentException("invalid email");
		customerRepository.save(customer);
	}

	public void deleteCustomerById(Integer id) {
		Boolean customerExist = customerRepository.existsById(id);
		if(!customerExist)
			throw new CustomerNotFoundException("customer id " + id + "not found.");
		customerRepository.deleteById(id);
	}
	
	@Transactional
	public void updateCustomer(Integer id, String name, String email) {
		if(!customerRepository.existsById(id))
			return;
		Customer customer = customerRepository.findById(id).get();
		if(name != null && !name.isEmpty() && !customer.getName().equals(name))
			customer.setName(name);
		if(email != null && !email.isEmpty() && !customer.getEmail().equals(email))
			if(customerRepository.findCustomerByEmail(email).isPresent())
				throw new CustomerEmailAlreadyExists("email exists");
			customer.setEmail(email);
	}
}
