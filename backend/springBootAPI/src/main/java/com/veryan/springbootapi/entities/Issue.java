package com.veryan.springbootapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Issue {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private User creator;
    @ManyToOne
    @JoinColumn(name = "assiginee")
    private User assiginee;
    @Column(nullable = false)
    private String title;
    @Column
    private String description;
    @Column(nullable = false)
    private LocalDate start;
    @Column
    private LocalDate end;
    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "subissues",
            joinColumns = {@JoinColumn(name = "issue")},
            inverseJoinColumns = {@JoinColumn(name = "subissue")}
    )
    List<Issue> subIssues;

    @ManyToMany(mappedBy = "issues")
    List<User> members;
}
