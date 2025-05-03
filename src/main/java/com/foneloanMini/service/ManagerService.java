package com.foneloanMini.service;

import com.foneloanMini.entity.Manager;
import com.foneloanMini.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    // Create a new manager
    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }

    // Retrieve a manager by email
    public Optional<Manager> getManagerByEmail(String email) {
        return managerRepository.findById(email);
    }

    public Manager updateManager(Manager manager) {
        return managerRepository.save(manager);
    }


    // Additional business logic specific to managers can be added here.
}
