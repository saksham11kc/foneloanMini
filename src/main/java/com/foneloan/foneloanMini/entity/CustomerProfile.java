package com.foneloan.foneloanMini.entity;

import jakarta.persistence.*;

//enum for IncoomeStability
enum IncomeStability {
    high, medium , low
}

@Entity
@Table(name ="customer_profile")
public class CustomerProfile {

    @Id
    // Here ID is derived from the User relationship i.e. No @GeneratedValue needed
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_credit" , nullable = false)
    private int totalCredit;


    // This is the owning side of the OneToOne relationship
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Maps the 'user' field's ID to this entity's ID (@Id column)
    @JoinColumn(name = "user_id") // Specifies the foreign key column
    private User user;


    @Column(name = "remaining_credit" , nullable = false)
    private int remainingCredit;

    @Column(name = "purpose_of_loan")
    private String purposeOfLoan;

    @Column(name="collateral_worth" , nullable = false)
    private int collateralWorth;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_stability")
    private IncomeStability incomeStability;


    public long getUserId() {
        return userId;
    }
    //no setter for userId usually needed when using @MapId

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public int getRemainingCredit() {
        return remainingCredit;
    }

    public void setRemainingCredit(int remainingCredit) {
        this.remainingCredit = remainingCredit;
    }

    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }

    public void setPurposeOfLoan(String purposeOfLoan) {
        this.purposeOfLoan = purposeOfLoan;
    }

    public int getCollateralWorth() {
        return collateralWorth;
    }

    public void setCollateralWorth(int collateralWorth) {
        this.collateralWorth = collateralWorth;
    }

    // Getter/Setter for User relationship
    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
        // You might set the derived userId here if needed, but @MapsId handles it
        // if (user != null) { this.userId = user.getUserId(); }
    }

    // Getter/Setter for incomeStability (using Enum)
    public IncomeStability getIncomeStability() { return incomeStability; }
    public void setIncomeStability(IncomeStability incomeStability) { this.incomeStability = incomeStability; }


    @Override
    public String toString() {
        return "CustomerProfile{" +
                "userId=" + userId + // This ID is mapped via @MapsId
                ", totalCredit=" + totalCredit +
                ", remainingCredit=" + remainingCredit +
                ", purposeOfLoan='" + purposeOfLoan + '\'' +
                ", collateralWorth=" + collateralWorth +
                ", incomeStability=" + incomeStability + // Use enum field
                '}';
    }
    // Optional: hashCode() and equals() based on userId are good practice
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerProfile that = (CustomerProfile) o;
        return java.util.Objects.equals(userId, that.userId); // Check ID equality
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(userId); // Use ID for hash
    }

}
