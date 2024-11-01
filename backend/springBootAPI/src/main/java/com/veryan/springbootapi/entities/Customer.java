package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "user")
    private User user;

    @Column
    private LocalDateTime created_date;

    @Column
    private String address;

    @Column
    private String password;

    public Customer(User user, String password, String address) {
        this.user = user;
        this.password = password;
        this.address = address;
    }

    public Customer() {

    }
}
