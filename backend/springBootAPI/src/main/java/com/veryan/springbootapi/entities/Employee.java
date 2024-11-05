package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

@Entity(name = "employees")
@Data
@EqualsAndHashCode
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @Column
    private String password;

    public Employee(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public Employee() {

    }

}
