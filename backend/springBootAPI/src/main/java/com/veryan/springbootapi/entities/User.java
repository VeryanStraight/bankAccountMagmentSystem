package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "users")
@Data
public class User {
    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "managers_subordinates",
            joinColumns = {@JoinColumn(name = "manager")},
            inverseJoinColumns = {@JoinColumn(name = "subordinate")}
    )
    private List<User> subordinates;

    @ManyToMany
    @JoinTable(
            name = "members_issues",
            joinColumns = {@JoinColumn(name = "member")},
            inverseJoinColumns = {@JoinColumn(name = "issue")}
    )
    List<Issue> issues;
}
