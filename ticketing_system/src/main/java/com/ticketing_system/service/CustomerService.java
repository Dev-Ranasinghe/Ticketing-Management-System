package com.ticketing_system.service;

import com.ticketing_system.entity.Customer;
import com.ticketing_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get a single customer
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Create or update a customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete a customer by ID
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public boolean customerVerification(String username, String password){
        boolean valid = false;
        Customer customer = customerRepository.findByCustomerEmail(username);
        if(customer == null){
            return false;
        }
        if(customer.getCustomerPassword().equals(password)){
            valid = true;
        }
        return valid;
    }
}