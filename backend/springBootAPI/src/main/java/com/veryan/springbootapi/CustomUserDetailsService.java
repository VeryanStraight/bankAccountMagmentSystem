package com.veryan.springbootapi;

import com.veryan.springbootapi.entities.Customer;
import com.veryan.springbootapi.entities.Employee;
import com.veryan.springbootapi.reposities.CustomerRepository;
import com.veryan.springbootapi.reposities.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * a custom user details service that finds the corresponding password and role from a username.
 * checks for an employee first then a customer.
 * it works under the assumption that a user can't be both a customer and employee.
 * doesn't use password encryption currently.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    /**
     * the constructor for CustomUserDetailsService
     * @param employeeRepository the repository for employees that allows access to the database
     * @param customerRepository the repository for customers that allows access to the database
     */
    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * finds the user details from the username
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException if no user is found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First, try to find the user in the Employee table
        Optional<Employee> employeeOpt = employeeRepository.findByUser_Username(username);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            return new User(
                    employee.getUser().getUsername(),
                    "{noop}"+employee.getPassword(), //prefix with {noop} as not encrypting password
                    Set.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
            );
        }

        // If not found, try the Customer table
        Optional<Customer> customerOpt = customerRepository.findByUser_Username(username);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            return new User(
                    customer.getUser().getUsername(),
                    "{noop}"+customer.getPassword(), //prefix with {noop} as not encrypting password
                    Set.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        // If no user is found, throw an exception
        throw new UsernameNotFoundException("User not found: " + username);
    }
}

