package com.veryan.springbootapi.reposities;

import com.veryan.springbootapi.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * the jpa user status
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
}
