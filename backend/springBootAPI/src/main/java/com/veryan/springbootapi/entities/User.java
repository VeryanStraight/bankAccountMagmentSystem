package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

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

    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public User() {

    }
}
