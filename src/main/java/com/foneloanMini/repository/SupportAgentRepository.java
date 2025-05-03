package com.foneloanMini.repository;

import com.foneloanMini.entity.SupportAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportAgentRepository extends JpaRepository<SupportAgent, String> {
    // Custom query methods specific to SupportAgent can be added here
}
