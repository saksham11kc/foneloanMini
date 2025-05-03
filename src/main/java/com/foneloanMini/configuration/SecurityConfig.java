package com.foneloanMini.configuration;

import com.foneloanMini.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity //It enables method level security annotation like @Preauthorize and @PostAuthorize
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //  Expose a NoOpPasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Register a DaoAuthenticationProvider that uses our UserDetailsService + the NoOp encoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }



    //  security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Make sure our auth provider is used
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // allow anyone to POST /api/customer (signup)
                        .requestMatchers(HttpMethod.POST, "/api/customer").permitAll()
                        // all other /api/customer/** require CUSTOMER role
                        .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                        // support‑agent endpoints
                        .requestMatchers("/api/support/**").hasRole("SUPPORT_AGENT")
                        // manager endpoints
                        .requestMatchers("/api/manager/**").hasRole("MANAGER")
                        // everything else just needs authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
