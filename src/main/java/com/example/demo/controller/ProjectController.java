package com.example.demo.controller;

import com.example.demo.domain.a.SomeManager;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    private final SomeManager projectManager;

    public ProjectController(SomeManager projectManager) {
        this.projectManager = projectManager;
    }
}