package com.foneloanMini.controller;

import com.foneloanMini.entity.Customer;
import com.foneloanMini.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create a new customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer created = customerService.createCustomer(customer);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Retrieve a customer by email
    @GetMapping("/{email}")
    @PreAuthorize("#email == authentication.name or hasRole('SUPPORT_AGENT') or hasRole('MANAGER')")
    public ResponseEntity<Customer> getCustomer(@PathVariable String email) {
        Optional<Customer> customerOpt = customerService.getCustomerByEmail(email);
        return customerOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update customer details
    @PutMapping("/{email}")
    @PreAuthorize("#email == authentication.name or hasRole('SUPPORT_AGENT') or hasRole('MANAGER')")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable String email,
            @RequestBody Customer incoming) {

        Optional<Customer> existingOpt = customerService.getCustomerByEmail(email);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Customer existing = existingOpt.get();

        // Only allow these fields to be updated:
        existing.setFirstName(incoming.getFirstName());
        existing.setLastName(incoming.getLastName());
        existing.setDob(incoming.getDob());

        Customer updated = customerService.updateCustomer(existing);
        return ResponseEntity.ok(updated);
    }

    // Delete a customer by email
    @DeleteMapping("/{email}")
    @PreAuthorize("#email == authentication.name or hasRole('SUPPORT_AGENT') or hasRole('MANAGER')")

    public ResponseEntity<Void> deleteCustomer(@PathVariable String email) {
        Optional<Customer> existingCustomer = customerService.getCustomerByEmail(email);
        if(existingCustomer.isPresent()){
            customerService.deleteCustomer(email);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PUT /api/customer/ram@gmail.com/applyloan?amount=500
    @PutMapping("/{email}/applyloan")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<Customer> applyLoan(@PathVariable String email, @RequestParam("amount") double loanAmount) {
        Optional<Customer> customerOpt = customerService.getCustomerByEmail(email);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerOpt.get();
        // Calculate available credit: creditAssigned - creditUsed
        double availableCredit = customer.getCreditAssigned() - customer.getCreditUsed();

        if (loanAmount > availableCredit) {
            // If requested loan exceeds available credit, return bad request.
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(customer);
        }

        // Increase creditUsed by the loan amount.
        customer.setCreditUsed(customer.getCreditUsed() + loanAmount);

        // Save the updated customer details.
        Customer updatedCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updatedCustomer);
    }


    @PatchMapping("/{email}")
    @PreAuthorize("#email == authentication.name or hasRole('SUPPORT_AGENT') or hasRole('MANAGER')")
    public ResponseEntity<Customer> patchCustomer(
            @PathVariable String email,
            @RequestBody Map<String, Object> updates) {

        // 1) Fetch existing customer
        Optional<Customer> opt = customerService.getCustomerByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Customer customer = opt.get();

        // 2) Apply allowed updates only
        if (updates.containsKey("firstName")) {
            customer.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            customer.setLastName((String) updates.get("lastName"));
        }
        if (updates.containsKey("dob")) {
            customer.setDob(LocalDate.parse((String) updates.get("dob")));
        }
        if (updates.containsKey("password")) {
            customer.setPassword((String) updates.get("password"));
        }

        // 3) Persist and return
        Customer updated = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updated);
    }
}
