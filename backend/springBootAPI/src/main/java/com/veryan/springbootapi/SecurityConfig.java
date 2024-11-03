package com.veryan.springbootapi;

import com.veryan.springbootapi.reposities.CustomerRepository;
import com.veryan.springbootapi.reposities.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/user").hasRole("EMPLOYEE")
                        .requestMatchers("/customer").hasRole("EMPLOYEE")
                        .requestMatchers("/employee").hasRole("EMPLOYEE")
                        .requestMatchers("/account").hasRole("EMPLOYEE")
                        .requestMatchers("/transaction").hasRole("EMPLOYEE")
                        .requestMatchers("/user/{username}").hasRole("EMPLOYEE")
                        .requestMatchers("/user/{username}").hasRole("CUSTOMER")
                        .requestMatchers("/user/{username}/customer").hasRole("EMPLOYEE")
                        .requestMatchers("/user/{username}/customer").hasRole("CUSTOMER")
                        .requestMatchers("/user/{username}/employee").hasRole("EMPLOYEE")
                        .requestMatchers("/account/{id}/transaction").hasRole("EMPLOYEE")
                        .requestMatchers("/account/{id}/transaction").hasRole("CUSTOMER")
                        .requestMatchers("/customer{id}/account").hasRole("EMPLOYEE")
                        .requestMatchers("/customer{id}/account").hasRole("CUSTOMER")
                        .requestMatchers("/user/{username}").hasRole("CUSTOMER")
                        .requestMatchers("/user/{username}").hasRole("EMPLOYEE")
                        .requestMatchers("/customer/{id}").hasRole("CUSTOMER")
                        .requestMatchers("/account/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/account/{id}").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
        return authenticationManagerBuilder.build();
    }
}
