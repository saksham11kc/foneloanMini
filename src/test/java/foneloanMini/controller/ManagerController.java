package com.foneloanMini.controller;

import com.foneloanMini.entity.Customer;
import com.foneloanMini.entity.Manager;
import com.foneloanMini.entity.SupportAgent;
import com.foneloanMini.service.CustomerService;
import com.foneloanMini.service.ManagerService;
import com.foneloanMini.service.SupportAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final SupportAgentService supportAgentService;
    private final CustomerService customerService;

    @Autowired
    public ManagerController(ManagerService managerService , SupportAgentService supportAgentService , CustomerService customerService) {
        this.managerService = managerService;
        this.supportAgentService = supportAgentService;
        this.customerService = customerService;
    }

    //Get customer info
    @GetMapping("customerinfo/{email}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String email) {
        Optional<Customer> customerOpt = customerService.getCustomerByEmail(email);
        return customerOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Get all customer info
    @GetMapping("/customerinfo")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }


    //Get agent info
    @GetMapping("/agentinfo/{email}")
    public ResponseEntity<SupportAgent> getSupportAgent(@PathVariable String email) {
        Optional<SupportAgent> agentOpt = supportAgentService.getSupportAgentByEmail(email);
        return agentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all agent info
        @GetMapping("/agentinfo")
        public ResponseEntity<List<SupportAgent>> getAllAgents() {
            List<SupportAgent> agents = supportAgentService.getAllAgents();
            return ResponseEntity.ok(agents);
        }



    // Update manager details
    @PutMapping("/{email}")
    @PreAuthorize("#email == authentication.name")
    public ResponseEntity<Manager> updateManager(@PathVariable String email, @RequestBody Manager manager) {
        Optional<Manager> existingManager = managerService.getManagerByEmail(email);
        if(existingManager.isPresent()){
            manager.setEmail(email);
            Manager updated = managerService.updateManager(manager);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PatchMapping for manager
    @PatchMapping("/{email}")
    @PreAuthorize("#email == authentication.name")
        public ResponseEntity<Manager> patchCustomer(
                @PathVariable String email,
                @RequestBody Map<String, Object> updates) {

            // 1) Fetch existing manager
            Optional<Manager> opt = managerService.getManagerByEmail(email);
            if (opt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Manager manager = opt.get();

            // 2) Apply allowed updates only
            if (updates.containsKey("firstName")) {
                manager.setFirstName((String) updates.get("firstName"));
            }
            if (updates.containsKey("lastName")) {
                manager.setLastName((String) updates.get("lastName"));
            }
            if (updates.containsKey("dob")) {
                manager.setDob(LocalDate.parse((String) updates.get("dob")));
            }
            if (updates.containsKey("password")) {
                manager.setPassword((String) updates.get("password"));
            }

            // 3) Persist and return
            Manager updated = managerService.updateManager(manager);
            return ResponseEntity.ok(updated);
        }


    @PatchMapping("/customerinfo/{email}/creditlimit")
    public ResponseEntity<Customer> updateCustomerCreditLimit(
            @PathVariable String email,
            @RequestBody Map<String, Object> payload) {

        // 1) Find the customer by email
        Optional<Customer> opt = customerService.getCustomerByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Customer customer = opt.get();

        // 2) Extract the new credit limit from the JSON body
        if (!payload.containsKey("creditAssigned")) {
            // If the JSON doesn't have "creditAssigned", return 400
            return ResponseEntity.badRequest().build();
        }
        Double newCredit = Double.parseDouble(payload.get("creditAssigned").toString());

        if (newCredit < customer.getCreditAssigned()) {
            throw new RuntimeException("More credit is already taken than newly assigned");
        }

        // 3) Update the customer's credit limit
        customer.setCreditAssigned(newCredit);

        // 4) Persist the change
        Customer updated = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updated);
    }

}

