package com.foneloanMini.service;

import com.foneloanMini.entity.Customer;
import com.foneloanMini.exception.ResourceAlreadyExistsException;
import com.foneloanMini.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    // Create a new customer; business logic can be added here.
    public Customer createCustomer(Customer customer) {
        // 1) Check if a Customer with this email already exists
        boolean exists = customerRepository.existsById(customer.getEmail());
        if (exists) {
            throw new ResourceAlreadyExistsException(
                    "Email '" + customer.getEmail() + "' is already used."
            );
        }

        // 2) If not, save the new Customer
        return customerRepository.save(customer);
    }

    // Retrieve a customer by email
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findById(email);
    }

    // Update customer details
    public Customer updateCustomer(Customer customer) {
        // You can add further validations or business logic before saving.
        return customerRepository.save(customer);
    }

    // Delete a customer by email
    public void deleteCustomer(String email) {
        customerRepository.deleteById(email);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
