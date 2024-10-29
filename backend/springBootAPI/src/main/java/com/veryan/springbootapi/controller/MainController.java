package com.veryan.springbootapi.controller;
import com.veryan.springbootapi.entities.Issue;
import com.veryan.springbootapi.entities.User;
import com.veryan.springbootapi.reposities.CategoryRepository;
import com.veryan.springbootapi.reposities.IssueRepository;
import com.veryan.springbootapi.reposities.StatusRepository;
import com.veryan.springbootapi.reposities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/issueTracking")
public class MainController {
    CategoryRepository categories;
    IssueRepository issues;
    StatusRepository statuses;
    UserRepository users;

    @Autowired
    public MainController(CategoryRepository categories, IssueRepository issues, StatusRepository statuses, UserRepository users) {
        this.categories = categories;
        this.issues = issues;
        this.statuses = statuses;
        this.users = users;
    }

    @GetMapping("/")
    public List<Issue> index() {
        return issues.findAll();
    }
}
