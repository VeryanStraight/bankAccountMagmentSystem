package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "accounts")
@Data
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
}
