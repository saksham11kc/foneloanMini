package com.foneloan.foneloanMini.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name= "email" , nullable=false , unique = true)
    private String email;

    @Column(name = "first_name" , nullable = false  )
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private LocalDate dob; //LocalDate class removes timezone issue and
    // stores only year , month , and day like that of DATE datatype in MYSQL

    @Column(name = "password" , nullable = false)
    private String password;

    @Column (name = "enabled" , nullable = false)
    private boolean enabled;


    // Relationship
    // ManyToMany relationship with Role
    @ManyToMany(fetch = FetchType.EAGER) // EAGER or LAZY depending on needs
    @JoinTable(
            name = "user_roles", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // FK column in join table for User
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK column in join table for Role
    )
    private Set<Role> roles = new HashSet<>(); // Initialize to avoid NullPointerExceptions

    // OneToOne relationship with CustomerProfile
    // 'mappedBy' indicates the field in CustomerProfile that owns the relationship
    // 'cascade = CascadeType.ALL' means operations (persist, remove, etc.) on User cascade to CustomerProfile
    // 'orphanRemoval = true' ensures the profile is deleted if removed from the user
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CustomerProfile customerProfile;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    // Getter/Setter for roles
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public String getPassword() {
        return password;
    }
    // Getter/Setter for customerProfile
    public CustomerProfile getCustomerProfile() { return customerProfile; }
    public void setCustomerProfile(CustomerProfile customerProfile) {
        // Ensure bidirectional link consistency
        if (customerProfile == null) {
            if (this.customerProfile != null) {
                this.customerProfile.setUser(null);
            }
        } else {
            customerProfile.setUser(this);
        }
        this.customerProfile = customerProfile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        // IMPORTANT: DO NOT include the password in toString() for security reasons!
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob + // Use dob directly now it's LocalDate
                // ", password='[PROTECTED]'" + // Exclude password
                ", enabled=" + enabled +
                ", roles=" + roles + // Can optionally include roles (might fetch lazily)
                '}';
    }
}
