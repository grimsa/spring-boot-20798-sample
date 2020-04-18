package com.example.demo.domain;

import com.example.demo.domain.a.ProjectManager;
import com.example.demo.domain.entities.BaseUser;
import com.example.demo.domain.entities.Project;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProjectManagerImpl implements ProjectManager, InitializingBean {
    private ProjectDao dao;

    public ProjectManagerImpl() {
    }

    public void setDao(ProjectDao dao) {
        this.dao = dao;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Bean created");
    }

    @Override
    public void doStuff(Project withThis) {
        System.out.println("Doing something" + withThis.getCreationDate());
        doSomethingWithOtherEntity(withThis.creator());
    }

    @Override
    public Project doStuffWithProject() {
        System.out.println("doing stuff ");
        return null;
    }

    public void doSomethingWithOtherEntity(BaseUser e) {
        System.out.println(e.greeting());
    }
}
