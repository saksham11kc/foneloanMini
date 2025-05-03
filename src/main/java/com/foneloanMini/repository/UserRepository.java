package com.foneloanMini.repository;

import com.foneloanMini.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Common query methods for all types of users can be added here
}
