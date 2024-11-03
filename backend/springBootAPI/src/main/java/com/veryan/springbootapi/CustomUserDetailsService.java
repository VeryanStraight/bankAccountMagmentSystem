package com.veryan.springbootapi;

import com.veryan.springbootapi.entities.Customer;
import com.veryan.springbootapi.entities.Employee;
import com.veryan.springbootapi.reposities.CustomerRepository;
import com.veryan.springbootapi.reposities.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;


    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First, try to find the user in the Employee table
        Optional<Employee> employeeOpt = employeeRepository.findByUser_Username(username);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("EMPLOYEE"));

            return new CustomUserDetails(
                    employee.getId(),
                    employee.getUser().getUsername(),
                    employee.getPassword(),
                    authorities
            );
        }

        // If not found, try the Customer table
        Optional<Customer> customerOpt = customerRepository.findByUser_Username(username);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("CUSTOMER"));

            return new CustomUserDetails(
                    customer.getId(),
                    customer.getUser().getUsername(),
                    customer.getPassword(),
                    authorities
            );
            //todo user cant be both user and employee
        }

        // If no user is found, throw an exception
        throw new UsernameNotFoundException("User not found: " + username);
    }
}

