package com.example.demo.controller;

import com.example.demo.domain.a.ProjectManager;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    private final ProjectManager projectManager;

    public ProjectController(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }
}