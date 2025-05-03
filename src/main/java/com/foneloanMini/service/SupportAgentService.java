package com.foneloanMini.service;

import com.foneloanMini.entity.SupportAgent;
import com.foneloanMini.repository.SupportAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportAgentService {

    private final SupportAgentRepository supportAgentRepository;

    @Autowired
    public SupportAgentService(SupportAgentRepository supportAgentRepository) {
        this.supportAgentRepository = supportAgentRepository;
    }

    // Create a new support agent
    public SupportAgent createSupportAgent(SupportAgent supportAgent) {
        return supportAgentRepository.save(supportAgent);
    }

    // Retrieve a support agent by email
    public Optional<SupportAgent> getSupportAgentByEmail(String email) {
        return supportAgentRepository.findById(email);
    }
    public SupportAgent updateSupportAgent(SupportAgent supportAgent) {
            return supportAgentRepository.save(supportAgent);
        }

    public List<SupportAgent> getAllAgents() {
        return supportAgentRepository.findAll();
    }

}
