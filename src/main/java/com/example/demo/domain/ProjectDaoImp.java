package com.example.demo.domain;

import com.example.demo.domain.entities.Project;
import org.springframework.stereotype.Component;

@Component("projectDao")
public class ProjectDaoImp extends AbstractDao<Project> implements ProjectDao {
    @Override
    public Project find() {
        return null;
    }
}
