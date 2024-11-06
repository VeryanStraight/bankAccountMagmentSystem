package com.veryan.springbootapi.reposities;

import com.veryan.springbootapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * the jpa user repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
