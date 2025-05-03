package com.foneloanMini.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @Column(name = "credit_assigned", nullable = false)
    private Double creditAssigned = 10000.0;

    @Column(name = "credit_used", nullable = false)
    private Double creditUsed = 0.0;

    // Constructors
    public Customer() {
        super();
    }

    public Customer(String email, String firstName, String lastName,
                    java.time.LocalDate dob, String password) {
        super(email, firstName, lastName, dob, password);
    }

    // Getters and Setters
    public Double getCreditAssigned() {
        return creditAssigned;
    }

    public void setCreditAssigned(Double creditAssigned) {
        this.creditAssigned = creditAssigned;
    }

    public Double getCreditUsed() {
        return creditUsed;
    }

    public void setCreditUsed(Double creditUsed) {
         this.creditUsed = creditUsed;
    }

    /**
     * Computed property: creditAssigned - creditUsed
     * Not persisted in the database.
     */
    @Transient // Tells spring boot not to persist to database i.e.don't save to database
    public Double getRemainingCredit() {
        return creditAssigned - creditUsed;
    }

    // Implement the abstract role method
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
}
