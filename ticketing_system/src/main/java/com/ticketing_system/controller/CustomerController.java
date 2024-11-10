package com.ticketing_system.controller;

import com.ticketing_system.entity.Customer;
import com.ticketing_system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        CustomerService.CustomerThread fetchAllThread = customerService.new CustomerThread("fetchAll", null, null);
        fetchAllThread.start();
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Integer id) {
        CustomerService.CustomerThread fetchByIdThread = customerService.new CustomerThread("fetchById", id, null);
        fetchByIdThread.start();
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        CustomerService.CustomerThread saveThread = customerService.new CustomerThread("save", null, customer);
        saveThread.start();
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        CustomerService.CustomerThread deleteThread = customerService.new CustomerThread("delete", id, null);
        deleteThread.start();
        customerService.deleteCustomer(id);
    }

    @GetMapping("/login")
    public boolean customerVerification(@RequestParam String username, @RequestParam String password) {
        return customerService.customerVerification(username, password);
    }
}


//package com.ticketing_system.controller;
//
//import com.ticketing_system.entity.Customer;
//import com.ticketing_system.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/customer")
//public class CustomerController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping
//    public List<Customer> getAllCustomers(){
//        return customerService.getAllCustomers();
//    }
//
//    @GetMapping("/{id}")
//    public Customer getCustomerById(@PathVariable Integer id) {
//        return customerService.getCustomerById(id);
//    }
//
//    @PostMapping
//    public Customer createCustomer(@RequestBody Customer customer) {
//        System.out.println(customer);
//        return customerService.saveCustomer(customer);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteCustomer(@PathVariable Integer id) {
//        customerService.deleteCustomer(id);
//    }
//
//    @GetMapping("/login")
//    public boolean customerVerification(@RequestParam String username, @RequestParam String password){
//        return customerService.customerVerification(username, password);
//    }
//}