package com.veryan.springbootapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

/**
 * the user entity
 */
@Entity(name = "users")
@Data
@EqualsAndHashCode
@ToString
@DynamicUpdate
public class User {
    @Id
    private String username;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    /**
     * the user constructor
     * @param username the username
     * @param name the name
     */
    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }

    /**
     * the no args constructor
     */
    public User() {

    }
}
