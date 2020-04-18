package com.example.demo.domain.a;

import com.example.demo.domain.b.Service;
import com.example.demo.domain.entities.Project;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectManager extends Service<Project> {

    @Transactional
    Project doStuffWithProject();
}
