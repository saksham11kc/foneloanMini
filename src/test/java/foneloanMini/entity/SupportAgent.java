package com.foneloanMini.entity;



import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SUPPORT_AGENT")
public class SupportAgent extends User {

    // No additional fields needed for support agent

    // Constructors
    public SupportAgent() {
        super();
    }

    public SupportAgent(String email, String firstName, String lastName,
                        java.time.LocalDate dob, String password) {
        super(email, firstName, lastName, dob, password);
    }
    @Override
    public String getRole() {
        return "SUPPORT_AGENT";
    }
}
