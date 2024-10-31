package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity(name = "users")
@Data
@EqualsAndHashCode
@ToString
public class User {
    @Id
    private String username;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }
}
