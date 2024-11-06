package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * the account entity
 */
@Entity(name = "accounts")
@Data
@EqualsAndHashCode
@ToString
@DynamicUpdate
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column
    private String name;

    @Column
    private LocalDateTime start;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "beneficiaries",
            joinColumns = {@JoinColumn(name = "account")},
            inverseJoinColumns = {@JoinColumn(name = "customer")}
    )
    private List<Customer> beneficiaries;

    /**
     * the constructor
     * @param customer the customer
     */
    public Account(Customer customer) {
        this.customer = customer;
    }

    /**
     * the default constructor
     */
    public Account() {}

    /**
     * add an amount of money to the account
     * @param amount the amount to add
     */
    public void addAmount(BigDecimal amount){
        balance = balance.add(amount);
    }

    /**
     * subtract an amount of money from the account
     * @param amount the amount to subtract
     */
    public void subtractAmount(BigDecimal amount){
        balance = balance.subtract(amount);
    }

}
