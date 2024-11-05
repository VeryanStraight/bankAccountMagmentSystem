package com.veryan.springbootapi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
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
                .loginProcessingUrl("/login") // Login processing URL
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"success\", \"message\": \"Login successful\"}");
                })
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Login failed\"}");
                })
//                .loginPage("/login") // Explicitly set to disable default login page
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:5174"); // Allow requests from React app
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply the config to all endpoints
        return source;
    }
}
