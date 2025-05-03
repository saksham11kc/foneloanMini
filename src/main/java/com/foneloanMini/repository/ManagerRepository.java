package com.foneloanMini.repository;

import com.foneloanMini.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {
    // Custom query methods specific to Manager can be added here
}
