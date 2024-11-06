package com.veryan.springbootapi.reposities;

import com.veryan.springbootapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * the jpa employee repository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUser_Username(String user);
    void deleteByUser_Username(String user);
}
