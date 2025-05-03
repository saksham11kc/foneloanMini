package com.foneloanMini.security;

import com.foneloanMini.entity.User;
import com.foneloanMini.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    //UserDetailsService is predefined in spring security for authorization and authentication.

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1) Find user by email in the DB
        User user = userRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 2) Map DB role (e.g. "CUSTOMER") to a SimpleGrantedAuthority with "ROLE_CUSTOMER"
        String dbRole = user.getRole(); // e.g. "CUSTOMER"
        String springSecurityRole = "ROLE_" + dbRole; // => "ROLE_CUSTOMER"

        // 3) Create a list of authorities
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(springSecurityRole);

        // 4) Return a Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
