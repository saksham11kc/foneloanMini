package com.foneloanMini.controller;

import com.foneloanMini.entity.Customer;
import com.foneloanMini.entity.SupportAgent;
import com.foneloanMini.entity.User;
import com.foneloanMini.service.CustomerService;
import com.foneloanMini.service.SupportAgentService;
import com.foneloanMini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/support")
public class SupportAgentController {

    private final UserService userService;
    private final SupportAgentService supportAgentService;
    private final CustomerService customerService;

    @Autowired
    public SupportAgentController(UserService userService,
                                  SupportAgentService supportAgentService,
                                  CustomerService customerService) {
        this.userService = userService;
        this.supportAgentService = supportAgentService;
        this.customerService = customerService;
    }

    // ----- User Lookups (For viewing any user's personal info) -----

    /**
     * Retrieve personal info for one specific user by email.
     * Example: GET /api/support/users/bijaya@gmail.com
     */
    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieve personal info for all customers.
     * Example: GET /api/support/users
     */
    @GetMapping("/users")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * PATCH endpoint for customer partial update.
     * Allows updating: password, firstName, lastName, dob only.
     * Example: PATCH /api/support/users/ram@gmail.com
     * Request body:
     * {
     *    "firstName": "Abhi",
     *    "lastName": "Thapa",
     *    "dob": "2001-01-01",
     *    "password": "abhi123"
     * }
     */
    @PatchMapping("/users/{email}")
    public ResponseEntity<Customer> patchCustomer(
            @PathVariable String email,
            @RequestBody Map<String, Object> updates) {

        Optional<Customer> custOpt = customerService.getCustomerByEmail(email);
        if (custOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Customer customer = custOpt.get();

        // Allowed updates for a customer:
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

        Customer updated = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updated);
    }

    // ----- Support Agent Management -----

    /**
     * Retrieve support agent info by email.
     * Example: GET /api/support/agents/agent@gmail.com
     */
    @GetMapping("/agents/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<SupportAgent> getSupportAgent(@PathVariable String email) {
        Optional<SupportAgent> agentOpt = supportAgentService.getSupportAgentByEmail(email);
        return agentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * PATCH endpoint for support agent partial update.
     * Allows updating: password, firstName, lastName, dob only.
     * Example: PATCH /api/support/agents/agent@gmail.com
     * Request body:
     * {
     *    "firstName": "Agent",
     *    "lastName": "Updated",
     *    "dob": "1990-01-01",
     *    "password": "newAgentPass"
     * }
     */
    @PatchMapping("/agents/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<SupportAgent> patchSupportAgent(
            @PathVariable String email,
            @RequestBody Map<String, Object> updates) {

        Optional<SupportAgent> opt = supportAgentService.getSupportAgentByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SupportAgent supportAgent = opt.get();

        // Allowed fields: firstName, lastName, dob, password
        if (updates.containsKey("firstName")) {
            supportAgent.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            supportAgent.setLastName((String) updates.get("lastName"));
        }
        if (updates.containsKey("dob")) {
            supportAgent.setDob(LocalDate.parse((String) updates.get("dob")));
        }
        if (updates.containsKey("password")) {
            supportAgent.setPassword((String) updates.get("password"));
        }

        SupportAgent updated = supportAgentService.updateSupportAgent(supportAgent);
        return ResponseEntity.ok(updated);
    }
}
