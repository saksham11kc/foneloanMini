package com.foneloanMini.entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {

    // No additional fields needed for manager

    // Constructors
    public Manager() {
        super();
    }

    public Manager(String email, String firstName, String lastName,
                   java.time.LocalDate dob, String password) {
        super(email, firstName, lastName, dob, password);
    }
    @Override
    public String getRole() {
        return "MANAGER";
    }
}
