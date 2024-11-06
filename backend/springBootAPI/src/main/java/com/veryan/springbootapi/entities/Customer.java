package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

/**
 * the customer entity
 */
@Entity(name = "customers")
@Data
@EqualsAndHashCode
@ToString
@DynamicUpdate
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column
    private String address;

    @Column
    private String password;

    /**
     * the constructor
     * @param user the user
     * @param password the password
     * @param address the address
     */
    public Customer(User user, String password, String address) {
        this.user = user;
        this.password = password;
        this.address = address;
    }

    /**
     * the default constructor
     */
    public Customer() {

    }
}
